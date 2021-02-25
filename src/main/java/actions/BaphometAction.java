package actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.PlayTopCardAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import patches.MainEnum;
import powers.DeadlyVelocityPlusPower;
import powers.DeadlyVelocityPower;

public class BaphometAction extends AbstractGameAction {


    private int stacks = 0;
    private int stacksPlus = 0;
    private AbstractPlayer p;
    //[] TEXT = CardCrawlGame.languagePack.getUIString("DiscardPileToHandAction").TEXT;


    public BaphometAction(int amount) {
        this.p = AbstractDungeon.player;
        setValues(this.p, AbstractDungeon.player, amount);
        this.actionType = ActionType.CARD_MANIPULATION;
    }

    public void update() {

        AbstractCard card;
        if(p.hasPower(DeadlyVelocityPower.POWER_ID))
        {
            stacks = p.getPower(DeadlyVelocityPower.POWER_ID).amount;
            addToBot(new RemoveSpecificPowerAction(p, p, DeadlyVelocityPower.POWER_ID));

        }
        if(p.hasPower(DeadlyVelocityPlusPower.POWER_ID))
        {
            stacksPlus = p.getPower(DeadlyVelocityPlusPower.POWER_ID).amount;
            addToBot(new RemoveSpecificPowerAction(p, p, DeadlyVelocityPlusPower.POWER_ID));

        }

        for(int i = 0; i < amount; i++) {

            /*
            card = p.drawPile.getTopCard();
            if(card.hasTag(MainEnum.SUMMON_CARD)){
                p.drawPile.removeCard(card);
                p.discardPile.addToRandomSpot(card);
                i--;
            }

            else
             */
                addToBot(new PlayTopCardAction(AbstractDungeon.getCurrRoom().monsters.getRandomMonster(null, true, AbstractDungeon.cardRandomRng), false));
        }
        if(stacks != 0)
            addToBot(new ApplyPowerAction(p, p, new DeadlyVelocityPower(p, stacks), stacks));
        if(stacksPlus != 0)
        addToBot(new ApplyPowerAction(p, p, new DeadlyVelocityPlusPower(p, stacksPlus), stacksPlus));

        p.hand.refreshHandLayout();
        isDone = true;
    }
}
