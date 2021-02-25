package powers;


import actions.GainAttuneAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.ThornsPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import mod.RitualistMod;

public class MaatPower extends AbstractPower {

    public static final String POWER_ID = RitualistMod.makeID("MaatPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = RitualistMod.makePath("customImages/buffTest.png");
    private int turns;
    private int count = 0;
    public int amounts[];
    public static final int MAX_TURNS = 3;


    public MaatPower(final AbstractCreature owner, int amount, int turns) {
        name = NAME;
        ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.amounts = new int[MAX_TURNS+1];
        this.amounts[0] = amount;
        this.amounts[1] = amount;
        this.amounts[2] = amount;

        this.turns = turns;
        updateDescription();
        type = PowerType.BUFF;
        isTurnBased = false;
        //img = new Texture(IMG);
        loadRegion("blur");
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
    }

    public int totalAmount() {
        int total = 0;
        total = amounts[0];
        return total;
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        /*
        if(isPlayer) {
            count++;
            addToBot(new GainBlockAction(owner, owner, amount));
            addToBot(new ApplyPowerAction(owner, owner, new ThornsPower(owner, this.thorns), this.thorns));

            updateDescription();

            if(count == (turns))
                thorns = thorns*(-3);
                addToBot(new ApplyPowerAction(owner, owner, new ThornsPower(owner, this.thorns), this.thorns));
                addToBot(new RemoveSpecificPowerAction(owner, owner, POWER_ID));

        }
         */
            this.flashWithoutSound();
            if (amounts[0] > 0) {
                addToBot(new GainBlockAction(owner, owner, amounts[0]));

            }
            // shift one over
            for (int i = 1; i < amounts.length; ++i) {
                amounts[i - 1] = amounts[i];
            }
            //delete last
            amounts[amounts.length - 1] = 0;
            // total amount to display
            this.amount = totalAmount();
            if (this.amount == 0) {
                addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
            } else {
                this.updateDescription();
            }

    }





}
