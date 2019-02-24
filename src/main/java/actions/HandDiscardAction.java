package actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.colorless.Shiv;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class HandDiscardAction extends AbstractGameAction {
    private boolean upgrade;

    public HandDiscardAction() {
        this.duration = Settings.ACTION_DUR_FAST;

    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            int theSize = AbstractDungeon.player.hand.size();
            AbstractDungeon.actionManager.addToBottom(new DiscardAction(AbstractDungeon.player, AbstractDungeon.player, theSize, false));

        }

        this.tickDuration();
    }

}
