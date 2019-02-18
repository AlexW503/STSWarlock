package actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.Iterator;

public class UpgradeTopDrawAction extends AbstractGameAction {

    private AbstractPlayer p;
    private int amount;

    public UpgradeTopDrawAction(int amount) {
        this.p = AbstractDungeon.player;
        setValues(this.p, AbstractDungeon.player, amount);
        this.actionType = ActionType.CARD_MANIPULATION;
        this.amount = amount;
    }

    public void update() {


            if(p.drawPile.group.size() == 1)
                amount = 1;
            if (p.drawPile.group.size() >= amount) {
                for (int i = amount; i > 0; i--) {
                    AbstractCard c = p.drawPile.getNCardFromTop(i-1);
                    if (c != null) {
                        if (c.canUpgrade())
                            c.upgrade();

                    }

                    isDone = true;
                }
            }

            tickDuration();
    }

}

