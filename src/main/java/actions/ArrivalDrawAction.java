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
        setValues(p, AbstractDungeon.player, amount);
        actionType = ActionType.CARD_MANIPULATION;
        duration = Settings.ACTION_DUR_FAST;
    }

    public void update() {
        Iterator<AbstractCard> c;
        AbstractCard card;
        if (duration == Settings.ACTION_DUR_FAST) {

            if (p.drawPile.isEmpty()) {
                isDone = true;
                return;
            }

            if (p.drawPile.size() == 1) {

                card = p.drawPile.getTopCard();

                if (card.hasTag(MainEnum.SUMMON_CARD)) {
                    card.unfadeOut();
                    p.hand.addToHand(card);

                    p.drawPile.removeCard(card);

                    card.unhover();
                    card.fadingOut = false;

                }
                isDone = true;
                return;
            } else {
                c = p.drawPile.group.iterator();

                while (c.hasNext()) {
                    card = c.next();
                    card.stopGlowing();
                    card.unhover();
                    card.unfadeOut();
                }

                c = p.drawPile.group.iterator();

                while(c.hasNext()) {
                    card = c.next();
                    if (!card.hasTag(MainEnum.SUMMON_CARD)) {
                        c.remove();
                        nonSums.add(card);
                    }
                }

                if (p.drawPile.isEmpty()) {
                    p.drawPile.group.addAll(nonSums);
                    nonSums.clear();
                    isDone = true;
                    return;
                } else {
                    AbstractDungeon.gridSelectScreen.open(p.drawPile, 1, TEXT, false);

                    tickDuration();
                }
            }
        } else {
            if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
                for (c = AbstractDungeon.gridSelectScreen.selectedCards.iterator(); c.hasNext(); card.unhover()) {
                    card = c.next();
                    p.hand.addToHand(card);

                    p.drawPile.removeCard(card);
                }

                AbstractDungeon.gridSelectScreen.selectedCards.clear();
                p.hand.refreshHandLayout();

                p.drawPile.group.addAll(nonSums);
                nonSums.clear();

                for (c = p.drawPile.group.iterator(); c.hasNext(); card.target_y = 0.0F) {
                    card = (AbstractCard) c.next();
                    card.unhover();
                    card.target_x = (float) CardGroup.DRAW_PILE_X;
                }
            }

            tickDuration();
        }

    }
}

