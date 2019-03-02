package actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.Iterator;

public class RandomDrawFetchAction extends AbstractGameAction {

    private AbstractPlayer p;
            //[] TEXT = CardCrawlGame.languagePack.getUIString("DiscardPileToHandAction").TEXT;



    public RandomDrawFetchAction(int amount) {
        this.p = AbstractDungeon.player;
        setValues(this.p, AbstractDungeon.player, amount);
        this.actionType = ActionType.CARD_MANIPULATION;
    }

    public void update() {
        AbstractCard c;
        for(int i = 0; i < amount; i++) {
            if (p.drawPile.size() == 0) {
                isDone = true;
                return;

            }
            else {
                c = p.drawPile.getRandomCard(true);
                p.drawPile.removeCard(c);
                p.hand.addToTop(c);

            }
        }
        p.hand.refreshHandLayout();
        isDone = true;
    }
 }



