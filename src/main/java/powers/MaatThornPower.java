package powers;


import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.ThornsPower;
import mod.RitualistMod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MaatThornPower extends AbstractPower {

    public static final String POWER_ID = RitualistMod.makeID("MaatThornPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = RitualistMod.makePath("customImages/maatPower.png");
    private int turns;
    private int count = 0;
    public int amounts[];
    public int maxThorns[];
    public static final Logger logger = LogManager.getLogger(RitualistMod.class.getName());


    public static final int MAX_TURNS = 3;


    public MaatThornPower(final AbstractCreature owner, int amount, int turns) {
        name = NAME;
        ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.amounts = new int[MAX_TURNS+1];
        this.amounts[0] = amount;
        this.amounts[1] = amount;
        this.amounts[2] = amount;
        this.maxThorns = new int[MAX_TURNS+1];
        this.maxThorns[turns-1] = amount*3;
        this.turns = turns;
        updateDescription();
        type = PowerType.BUFF;
        isTurnBased = false;
        img = new Texture(IMG);
       // loadRegion("blur");
       // source = source;

    }
    @Override
    public void updateDescription(){
        this.description = "";
        for (int i = 0; i < amounts.length-1; i++) {
            if (amounts[i] > 0) {    } if (!this.description.isEmpty()) this.description += " ";
            int turns = i;
            this.description += DESCRIPTIONS[0] + amounts[i] + (turns == 0 ? DESCRIPTIONS[1] : DESCRIPTIONS[2] + turns + (turns == 1 ? DESCRIPTIONS[3] : DESCRIPTIONS[4]));
        }
    }

    @Override
    public void stackPower(int stackAmount) {
        stackPower(stackAmount, MAX_TURNS);
    }
    public void stackPower(int stackAmount, int turns) {
        amounts[0] += stackAmount;
        amounts[1] += stackAmount;
        amounts[2] += stackAmount;
        amount = totalAmount();
        maxThorns[turns-1] += stackAmount*3;

    }

    public int totalAmount() {
        int total = 0;
        total = amounts[0];
        return total;
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
            this.flashWithoutSound();
            if (amounts[0] > 0) {
                addToBot(new ApplyPowerAction(owner, owner, new ThornsPower(owner, amounts[0]), amounts[0]));

            }
        logger.info("max thorns 0: " + maxThorns[0]);

        if(maxThorns[0] > 0)
                addToBot(new ApplyPowerAction(owner, owner, new RemoveThornsPower(owner, maxThorns[0]), maxThorns[0]));

        // shift one over
            for (int i = 1; i < amounts.length; ++i) {
                amounts[i - 1] = amounts[i];
                maxThorns[i - 1] = maxThorns[i];
                logger.info("max thorns i :" + maxThorns[i]);

            }
            //delete last
            amounts[amounts.length - 1] = 0;
            maxThorns[amounts.length - 1] = 0;

        // total amount to display
            this.amount = totalAmount();
            if (this.amount == 0) {
                addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
            } else {
                this.updateDescription();
            }

    }





}
