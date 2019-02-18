package actions;

import basemod.BaseMod;
import characters.Ritualist;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import mod.RitualistMod;

import java.util.ArrayList;
import java.util.Map;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.cardRandomRng;
import static patches.MainEnum.RITUAL_CARD;

public class GetRandomRitualAction extends AbstractGameAction {

    private AbstractPlayer p;
    ArrayList<AbstractCard> tmpPool= new ArrayList<>();
    int amount;

            //= new ArrayList<>();


    public GetRandomRitualAction(int amount) {
        this.p = AbstractDungeon.player;
        setValues(this.p, AbstractDungeon.player, amount);
        this.amount = amount;
        this.actionType = ActionType.CARD_MANIPULATION;
    }

    public void update(){

        AbstractCard card;

        /*
        for (Map.Entry<String, AbstractCard> c : CardLibrary.cards.entrySet()) {
            card = c.getValue();
            AbstractCard.CardColor color = AbstractDungeon.player.getCardColor();
            if (card.color.equals(color) && card.rarity != AbstractCard.CardRarity.BASIC && card.hasTag(RITUAL_CARD) && (!UnlockTracker.isCardLocked(c.getKey()))) {
                tmpPool.add(card);
            }
        }
        */

        for(AbstractCard c : CardLibrary.getAllCards()) {
            if(c.hasTag(RITUAL_CARD) &&  c.rarity != AbstractCard.CardRarity.BASIC){
                tmpPool.add(c);
            }
        }

        for(int i = amount; i>0; i--) {
            card = tmpPool.get(cardRandomRng.random(tmpPool.size() - 1));
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(card, 1));
        }
        isDone = true;
    }
}
