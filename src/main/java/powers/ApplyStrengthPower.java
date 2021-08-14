package powers;


import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import mod.RitualistMod;

public class ApplyStrengthPower extends AbstractPower {

    public static final String POWER_ID = RitualistMod.makeID("ApplyStrengthPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = RitualistMod.makePath("customImages/applyStr.png");

    public ApplyStrengthPower(final AbstractCreature owner, int amount) {
        name = NAME;
        ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        updateDescription();
        type = PowerType.BUFF;
        isTurnBased = false;
        //loadRegion("noPain");
        img = new Texture(IMG);
       // source = source;

    }


    @Override
    public void updateDescription(){
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }


    @Override
    public void atStartOfTurn() { //Remove this power and add Twisted Ecstasy

        addToBot(new ApplyPowerAction(owner, owner, new StrengthPower(owner, amount), amount));
        addToBot(new ApplyPowerAction(owner, owner, new LoseStrengthPower(owner, amount), amount));
        addToBot(new RemoveSpecificPowerAction(owner, owner, POWER_ID));


    }



}
