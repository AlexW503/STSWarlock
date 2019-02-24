package actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.Iterator;

public class DrawPileToTopDrawAction extends AbstractGameAction {

    private AbstractPlayer p;
    public static final String TEXT = "Select a card to place on top of your draw pile";
            //[] TEXT = CardCrawlGame.languagePack.getUIString("DiscardPileToHandAction").TEXT;



    public DrawPileToTopDrawAction(int amount) {
        this.p = AbstractDungeon.player;
        setValues(this.p, AbstractDungeon.player, amount);
        this.actionType = ActionType.CARD_MANIPULATION;
    }

    public void update() {
        if (this.p.drawPile.size() <= 1) {
            this.isDone = true;
            return;

        } else if (this.duration == 0.5F) {
            AbstractDungeon.gridSelectScreen.open(this.p.drawPile, this.amount, TEXT , false);
            this.tickDuration();
        } else {
            if (AbstractDungeon.gridSelectScreen.selectedCards.size() != 0) {
                Iterator var1 = AbstractDungeon.gridSelectScreen.selectedCards.iterator();

                AbstractCard c;
                while(var1.hasNext()) {
                    c = (AbstractCard)var1.next();
                    this.p.drawPile.removeCard(c);
                    this.p.drawPile.addToTop(c);
                    c.lighten(false);
                    c.unhover();
                }

                AbstractDungeon.gridSelectScreen.selectedCards.clear();
                this.p.hand.refreshHandLayout();

                for(var1 = this.p.drawPile.group.iterator(); var1.hasNext(); c.target_y = 0.0F) {
                    c = (AbstractCard)var1.next();
                    c.unhover();
                    c.target_x = (float)CardGroup.DRAW_PILE_X;
                }

                this.isDone = true;
            }

            this.tickDuration();
        }

    }
}

