package actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.ui.panels.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.actions.common.*;
import powers.AttunePower;

public class SetPowerZeroAction extends AbstractGameAction {

    private String id;
    public SetPowerZeroAction(final AbstractPlayer target, final AbstractPlayer source, final String powerId)
    {
        setValues(target, source);
        actionType = ActionType.REDUCE_POWER;
        id = powerId;

    }

    public void update(){
        if (target.hasPower(id))
        {
            target.getPower(id).amount = 0;
            target.getPower(id).updateDescription();
        }

        isDone = true;
    }
}
