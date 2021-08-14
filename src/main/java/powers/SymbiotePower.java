package powers;


import actions.GainAttuneAction;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.unique.RipAndTearAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import mod.RitualistMod;
import patches.MainEnum;

import java.util.UUID;

public class SymbiotePower extends AbstractPower implements NonStackablePower {
    public AbstractCreature source;

    public static final String POWER_ID = RitualistMod.makeID("SymbiotePower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = RitualistMod.makePath("customImages/symbiotePower.png");
    int att = 0;
    int index = 0;
    UUID id;
    String cName;
    boolean refund;

    public SymbiotePower(final AbstractCreature owner, UUID uuid, String cardName) {
        name = NAME;
        ID = POWER_ID;
        this.owner = owner;
        this.amount = 0;
        type = PowerType.BUFF;
        isTurnBased = false;
        img = new Texture(IMG);
        //loadRegion("beat");
        id = uuid;
        cName= cardName;
        refund = false;
        updateDescription();

    }

    /*
    private boolean checkArray(UUID toCheck){
        for(UUID element : ids)
            if(element == toCheck)
                return true;

        return false;
    }
*/
    @Override
    public void updateDescription() {
        if(amount == 0)
            description =  cName + DESCRIPTIONS[0];
        else
            description =  cName + DESCRIPTIONS[0] + DESCRIPTIONS[1] + amount + DESCRIPTIONS[2];


    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action){
        if(id == card.uuid) { // check if card played is in the array
            action.exhaustCard = false;

            if (owner.hasPower(AttunePower.POWER_ID)) {
                if(amount != 0)
                    amount += owner.getPower(AttunePower.POWER_ID).amount;
                else
                    amount = owner.getPower(AttunePower.POWER_ID).amount;
            }

            refund = true;
            updateDescription();
        }
   }
/*
   @Override
   public void onAfterCardPlayed(AbstractCard usedCard) {
       if(usedCard.hasTag(MainEnum.SUMMON_CARD))
           addToBot(new GainAttuneAction(att));


   }
*/
    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if(refund) {
            addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new AttunePower(AbstractDungeon.player, amount), amount));
            refund = false;
            amount = 0;
            updateDescription();
        }
    }
}
