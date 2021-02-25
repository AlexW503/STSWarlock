package actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import patches.MainEnum;

public class CarefulStudyAction extends AbstractGameAction {
    private int blockGain;

    public CarefulStudyAction(int blockGain) {
        duration = 0.0F;
        actionType = ActionType.WAIT;
        this.blockGain = blockGain;
    }

    public void update() {
        if (AbstractDungeon.player.drawPile.isEmpty()) {
            isDone = true;
        } else {
            AbstractCard card = AbstractDungeon.player.drawPile.getTopCard();
            if (card.hasTag(MainEnum.RITUAL_CARD)) {
                addToBot(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, this.blockGain));
            }

            isDone = true;
        }
    }
}
