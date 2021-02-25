package actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import mod.RitualistMod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.cardRandomRng;
import static patches.MainEnum.RITUAL_CARD;
import static patches.MainEnum.SUMMON_CARD;

public class RandomSummonToDeckAction extends AbstractGameAction {

    private AbstractPlayer p;
    ArrayList<AbstractCard> tmpPool= new ArrayList<>();
    int amount;


    public RandomSummonToDeckAction(int amount) {
        this.p = AbstractDungeon.player;
        setValues(this.p, AbstractDungeon.player, amount);
        this.amount = amount;
        this.actionType = ActionType.CARD_MANIPULATION;
    }

    public void update(){

        AbstractCard card;

        for(AbstractCard c : CardLibrary.getAllCards()) {
            if(c.hasTag(SUMMON_CARD)){ // &&  c.rarity != AbstractCard.CardRarity.BASIC
                tmpPool.add(c);
            }
        }

        for(int i = amount; i>0; i--) {
            card = tmpPool.get(cardRandomRng.random(tmpPool.size() - 1));
            addToBot(new MakeTempCardInDrawPileAction(card, 1, true, true));
            amount--;
        }
        isDone = true;
    }
}
