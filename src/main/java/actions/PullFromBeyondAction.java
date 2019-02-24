package actions;

import cards.PullFromBeyond;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.screens.select.GridCardSelectScreen;
import java.util.ArrayList;
import java.util.Iterator;

public class PullFromBeyondAction extends AbstractGameAction {

    private AbstractPlayer p;
    private final boolean upgrade;
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("ExhumeAction");
   // public static final String[] TEXT = uiStrings.TEXT;
    public static final String TEXT = "Select a card to add to your deck.";

    public PullFromBeyondAction(boolean upgrade) {
        this.upgrade = upgrade;
        this.p = AbstractDungeon.player;
        setValues(this.p, AbstractDungeon.player, this.amount);
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    public void update() {
        Iterator<AbstractCard> c;
        AbstractCard card;
        if (this.duration == Settings.ACTION_DUR_FAST) {

            if (this.p.exhaustPile.isEmpty()) {
                this.isDone = true;
                return;
            }

            if (this.p.exhaustPile.size() == 1) {
                card = this.p.exhaustPile.getTopCard();
                card.unfadeOut();
                this.p.drawPile.addToRandomSpot(card);

                this.p.exhaustPile.removeCard(card);

                card.unhover();
                card.fadingOut = false;
                this.isDone = true;
                return;
            } else {
                c = this.p.exhaustPile.group.iterator();

                while (c.hasNext()) {
                    card = c.next();
                    card.stopGlowing();
                    card.unhover();
                    card.unfadeOut();
                }

                c = this.p.exhaustPile.group.iterator();

                if (this.p.exhaustPile.isEmpty()) {
                    this.isDone = true;
                    return;
                } else {
                    AbstractDungeon.gridSelectScreen.open(this.p.exhaustPile, 1, TEXT, false);
                    this.tickDuration();
                }
            }
        } else {
            if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
                for (c = AbstractDungeon.gridSelectScreen.selectedCards.iterator(); c.hasNext(); card.unhover()) {
                    card = c.next();
                    this.p.drawPile.addToRandomSpot(card);

                    this.p.exhaustPile.removeCard(card);
                    /*
                    if (this.upgrade && card.canUpgrade()) {
                        card.upgrade();
                    }*/
                }

                AbstractDungeon.gridSelectScreen.selectedCards.clear();
                //this.p.hand.refreshHandLayout();


                for (c = this.p.exhaustPile.group.iterator(); c.hasNext(); card.target_y = 0.0F) {
                    card = (AbstractCard) c.next();
                    card.unhover();
                    card.target_x = (float) CardGroup.DISCARD_PILE_X;
                }
            }

            this.tickDuration();
        }

    }
}

