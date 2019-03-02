package actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import powers.PossessionPower;

public class ExtraditionAction extends AbstractGameAction {
    private int vuln;
    private DamageInfo info;
    private static final float DURATION = 0.01F;
    private static final float POST_ATTACK_WAIT_DUR = 0.2F;


    public ExtraditionAction(AbstractCreature target, int amount) {
        this.target = target;
        this.actionType = ActionType.DEBUFF;
        this.attackEffect = AttackEffect.SLASH_HORIZONTAL;
        this.duration = 0.01F;
        this.amount = amount;

    }

    public void update() {
        if (this.target == null) {
            this.isDone = true;
        } else if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
            AbstractDungeon.actionManager.clearPostCombatActions();
            this.isDone = true;
        } else {
            if (this.target.currentHealth > 0) {
                AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(target, target, new PossessionPower(target, target, amount), amount));
                }
        }

            this.isDone = true;
    }
}

