package powers;


import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.EnergizedPower;
import mod.RitualistMod;

public class ApplyEnergizedPower extends AbstractPower {

    public static final String POWER_ID = RitualistMod.makeID("ApplyEnergizedPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
  //  public static final String IMG = RitualistMod.makePath("customImages/buffTest.png");

    public ApplyEnergizedPower(final AbstractCreature owner, int amount) {
        name = NAME;
        ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        description = DESCRIPTIONS[0];
        type = PowerType.BUFF;
        isTurnBased = false;
        updateDescription();
        //img = new Texture(IMG);
        loadRegion("energized_blue");
       // source = source;

    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    @Override
    public void atStartOfTurnPostDraw() { //Remove this power and add energized

        addToBot
                (new ApplyPowerAction(owner, owner, new EnergizedPower(owner, this.amount)));
        addToBot
                (new RemoveSpecificPowerAction(owner, owner, POWER_ID));


    }



}
