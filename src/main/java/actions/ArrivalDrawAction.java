package actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import patches.MainEnum;

import java.util.ArrayList;
import java.util.Iterator;

public class ArrivalDrawAction extends AbstractGameAction {

    private AbstractPlayer p;
   // private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("ExhumeAction");
    public static final String TEXT = "Select a Summon to add to your hand";
            //[] TEXT = uiStrings.TEXT;
    private ArrayList<AbstractCard> nonSums = new ArrayList();

    public ArrivalDrawAction() {
        this.p = AbstractDungeon.player;
        setValues(this.p, AbstractDungeon.player, this.amount);
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    public void update() {
        Iterator<AbstractCard> c;
        AbstractCard card;
        if (this.duration == Settings.ACTION_DUR_FAST) {

            if (this.p.drawPile.isEmpty()) {
                this.isDone = true;
                return;
            }

            if (this.p.drawPile.size() == 1) {

                card = this.p.drawPile.getTopCard();

                if (card.hasTag(MainEnum.SUMMON_CARD)) {
                    card.unfadeOut();
                    this.p.hand.addToHand(card);

                    this.p.drawPile.removeCard(card);

                    card.unhover();
                    card.fadingOut = false;

                }
                this.isDone = true;
                return;
            } else {
                c = this.p.drawPile.group.iterator();

                while (c.hasNext()) {
                    card = c.next();
                    card.stopGlowing();
                    card.unhover();
                    card.unfadeOut();
                }

                c = this.p.drawPile.group.iterator();

                while(c.hasNext()) {
                    card = c.next();
                    if (!card.hasTag(MainEnum.SUMMON_CARD)) {
                        c.remove();
                        this.nonSums.add(card);
                    }
                }

                if (this.p.drawPile.isEmpty()) {
                    this.p.drawPile.group.addAll(this.nonSums);
                    this.nonSums.clear();
                    this.isDone = true;
                    return;
                } else {
                    AbstractDungeon.gridSelectScreen.open(this.p.drawPile, 1, TEXT, false);
                    this.tickDuration();
                }
            }
        } else {
            if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
                for (c = AbstractDungeon.gridSelectScreen.selectedCards.iterator(); c.hasNext(); card.unhover()) {
                    card = c.next();
                    this.p.hand.addToHand(card);

                    this.p.drawPile.removeCard(card);
                    /*
                    if (this.upgrade && card.canUpgrade()) {
                        card.upgrade();
                    }*/
                }

                AbstractDungeon.gridSelectScreen.selectedCards.clear();
                this.p.hand.refreshHandLayout();


                for (c = this.p.drawPile.group.iterator(); c.hasNext(); card.target_y = 0.0F) {
                    card = (AbstractCard) c.next();
                    card.unhover();
                    card.target_x = (float) CardGroup.DRAW_PILE_X;
                }
            }

            this.tickDuration();
        }

    }
}

