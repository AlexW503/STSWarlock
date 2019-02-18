package actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;


public class ThoughtfulArtAction  extends AbstractGameAction {
    private int cardPlayCount = 0;
    private int block = 0;

    public ThoughtfulArtAction(int cardPlayCount, int block) {
        this.cardPlayCount = cardPlayCount;
        this.block = block;
    }

    public void update(){
        if (AbstractDungeon.actionManager.cardsPlayedThisTurn.size() > this.cardPlayCount) {
            AbstractDungeon.actionManager.addToTop(new DrawCardAction(AbstractDungeon.player, 1));
            AbstractDungeon.actionManager.addToTop(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, this.block));
        }

        this.isDone = true;

    }

}
