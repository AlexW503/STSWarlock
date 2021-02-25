package powers;


import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.ThornsPower;
import mod.RitualistMod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RemoveThornsPower extends AbstractPower {

    public static final String POWER_ID = RitualistMod.makeID("RemoveThornsPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = RitualistMod.makePath("customImages/buffTest.png");
    public static final Logger logger = LogManager.getLogger(RitualistMod.class.getName());


    public RemoveThornsPower(final AbstractCreature owner, int thorns) {
        name = NAME;
        ID = POWER_ID;
        this.owner = owner;
        this.amount = thorns;
        updateDescription();
        type = PowerType.BUFF;
        isTurnBased = false;
        img = new Texture(IMG);
       // source = source;



    }
    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    @Override
    public void atStartOfTurn() {
        if(owner.getPower(ThornsPower.POWER_ID).amount <= amount)
            addToBot(new RemoveSpecificPowerAction(owner, owner, ThornsPower.POWER_ID));
        else
            addToBot(new ApplyPowerAction(owner, owner, new ThornsPower(owner, -amount), -amount));



        addToBot(new RemoveSpecificPowerAction(owner, owner, POWER_ID));

    }





}
