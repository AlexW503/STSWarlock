package powers;


import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.*;
import mod.RitualistMod;

public class DeadlyVelocityPlusPower extends AbstractPower {
    public AbstractCreature source;

    public static final String POWER_ID = RitualistMod.makeID("DeadlyVelocityPlusPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = RitualistMod.makePath("customImages/velocityBuff.png");
    private int count = 0;
    public int maxCount = 2;

    public DeadlyVelocityPlusPower(final AbstractCreature owner, final int amount) {
        name = NAME;
        ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        updateDescription();
        type = PowerType.BUFF;
        isTurnBased = false;
        img = new Texture(IMG);
       // source = source;


    }



    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1] + (maxCount-count) + DESCRIPTIONS[2];
    }

   @Override
    public void onUseCard(AbstractCard card, UseCardAction action){
       addToBot(new ApplyPowerAction(owner, owner, new StrengthPower(owner, amount), amount));
       addToBot(new ApplyPowerAction(owner, owner, new LoseStrengthPower(owner, amount), amount));

       count++;
       if(count == maxCount){
           addToBot(new ApplyPowerAction(owner, owner, new DexterityPower(owner, amount), amount));
           addToBot(new ApplyPowerAction(owner, owner, new LoseDexterityPower(owner, amount), amount));
            count = 0;

       }
        updateDescription();
       }

}
