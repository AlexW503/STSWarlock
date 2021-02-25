package actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import powers.AttunePower;
import powers.GrowingRitesPower;

public class GainAttuneAction extends AbstractGameAction {
    private int amount;


    public GainAttuneAction(int amount) {
        this.amount = amount;
    }

    public void update() {
       // if(!AbstractDungeon.player.hasPower(GrowingRitesPower.POWER_ID))
            addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new AttunePower(AbstractDungeon.player, amount), amount));
        isDone = true;

    }
}
