package actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;

import java.util.ArrayList;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.cardRandomRng;
import static patches.MainEnum.RITUAL_CARD;

public class RandomCurseToDiscardAction extends AbstractGameAction {

    private AbstractPlayer p;
    ArrayList<AbstractCard> tmpPool= new ArrayList<>();
    int amount;

            //= new ArrayList<>();


    public RandomCurseToDiscardAction(int amnt) {
        this.p = AbstractDungeon.player;
        setValues(this.p, AbstractDungeon.player, amount);
        this.amount = amnt;
        this.actionType = ActionType.CARD_MANIPULATION;
    }

    public void update(){

        AbstractCard card;

        for(AbstractCard c : CardLibrary.getAllCards()) {
            if(c.type == AbstractCard.CardType.CURSE){
                tmpPool.add(c);
            }
        }

        for(int i = amount; i>0; i--) {
            card = tmpPool.get(cardRandomRng.random(tmpPool.size() - 1));
            addToBot(new MakeTempCardInDiscardAction(card, amount));
        }
        isDone = true;
    }
}
