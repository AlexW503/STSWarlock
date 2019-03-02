package actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;

import java.util.ArrayList;
import java.util.Iterator;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.cardRandomRng;
import static patches.MainEnum.RITUAL_CARD;

public class DiscardToHandFullAction extends AbstractGameAction {

    private AbstractPlayer p;
    //[] TEXT = CardCrawlGame.languagePack.getUIString("DiscardPileToHandAction").TEXT;
    private int handSize;


    public DiscardToHandFullAction() {
        this.p = AbstractDungeon.player;
        setValues(this.p, AbstractDungeon.player);
        this.actionType = ActionType.CARD_MANIPULATION;
    }

    public void update() {
        AbstractCard c;
        handSize = AbstractDungeon.player.hand.size();
        amount = 10-handSize;
        for(int i = 0; i < amount; i++) {
            if (p.discardPile.size() == 0) {
                isDone = true;
                return;

            }
            else {
                c = p.discardPile.getRandomCard(true);
                p.discardPile.removeCard(c);
                p.hand.addToTop(c);

            }
        }
        p.hand.refreshHandLayout();
        isDone = true;
    }
}
