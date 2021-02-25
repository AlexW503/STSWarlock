package actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import mod.RitualistMod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import powers.AttunePower;

public class EmptyHandHealAction extends AbstractGameAction {
    private int amount;
    public static final Logger logger = LogManager.getLogger(RitualistMod.class.getName());
    AbstractPlayer p = AbstractDungeon.player;



    public EmptyHandHealAction(int amount) {
        this.amount = amount;
    }

    public void update() {
        logger.info(p.hand.size());
        if(p.hand.size() == 0) {
            addToBot(new HealAction(p, p, amount));
        }
        isDone = true;

    }
}
