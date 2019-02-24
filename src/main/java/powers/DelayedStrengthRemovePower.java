package powers;


import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import mod.RitualistMod;

public class DelayedStrengthRemovePower extends AbstractPower {

    public static final String POWER_ID = RitualistMod.makeID("DelayedStrengthRemovePower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = RitualistMod.makePath("customImages/buffTest.png");
    private int strLoss = 0;

    public DelayedStrengthRemovePower(final AbstractCreature owner, int turns, int loss) {
        name = NAME;
        ID = POWER_ID;
        this.owner = owner;
        this.amount = turns; // number of turns til strength loss
        strLoss = loss;
        updateDescription();
        type = PowerType.BUFF;
        isTurnBased = false;
        img = new Texture(IMG);
       // source = source;



    }
    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1] + strLoss + DESCRIPTIONS[2];
    }

    @Override
    public void atStartOfTurn() { //Each turn remove a stack. If gone then remove the strength.
        amount--;
        updateDescription();
        if(amount <= 1) {

            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(owner, owner, new GainStrengthPower(owner, -strLoss), -strLoss));
            AbstractDungeon.actionManager.addToBottom
                    (new RemoveSpecificPowerAction(owner, owner, POWER_ID));
        }

    }



}
