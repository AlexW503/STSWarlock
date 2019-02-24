package powers;


import actions.GainAttuneAction;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import mod.RitualistMod;

public class DelayedPossessionRemovePower extends AbstractPower {

    public static final String POWER_ID = RitualistMod.makeID("DelayedPossessionRemovePower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = RitualistMod.makePath("customImages/buffTest.png");
    public static final int MAX_TURNS = 3;

    public int amounts[];



    public DelayedPossessionRemovePower(final AbstractCreature owner, int amount, int turns) {
        name = NAME;
        ID = POWER_ID;
        this.owner = owner;
        this.amount = amount; // displayed amount is total amount of bomb damage
        this.amounts = new int[MAX_TURNS+1];
        this.amounts[turns] = amount;
        updateDescription();
        type = PowerType.BUFF;
        isTurnBased = true;
        img = new Texture(IMG);
       // source = source;



    }
    @Override
    public void updateDescription() {
        this.description = "";
        for (int i = 0; i < amounts.length; i++) {
            if (amounts[i] > 0) {
                if (!this.description.isEmpty()) this.description += " ";
                int turns = i;
                this.description += DESCRIPTIONS[0] + amounts[i] + (turns == 0 ? DESCRIPTIONS[1] : DESCRIPTIONS[2] + turns + (turns == 1 ? DESCRIPTIONS[3] : DESCRIPTIONS[4]));
            }
        }
    }

    @Override
    public void stackPower(int stackAmount) {
        stackPower(stackAmount, MAX_TURNS);
    }
    public void stackPower(int stackAmount, int turns) {
        amounts[turns] += stackAmount;
        amount = totalAmount();
    }

    public int totalAmount() {
        int total = 0;
        for (int i = 0; i < amounts.length; i++) {
            total += amounts[i];
        }
        return total;
    }

    @Override
    public void atStartOfTurn() {
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT && !AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            this.flashWithoutSound();
            if (amounts[0] > 0) {
                AbstractDungeon.actionManager.addToBottom(new GainAttuneAction(amounts[0]));
            }
            // shift one over
            for (int i = 1; i < amounts.length; ++i) {
                amounts[i - 1] = amounts[i];
            }
            amounts[amounts.length - 1] = 0;
            // total amount to display
            this.amount = totalAmount();
            if (this.amount == 0) {
                AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
            } else {
                this.updateDescription();
            }
        }
    }


}
