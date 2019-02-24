package actions;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.GetAllInBattleInstances;
import java.util.Iterator;
import java.util.UUID;


public class ModifyMagicNumAction extends AbstractGameAction {
    UUID uuid;

    public ModifyMagicNumAction(UUID targetUUID, int amount){
        setValues(target, source, amount);
        actionType = ActionType.CARD_MANIPULATION;
        uuid = targetUUID;
    }

    public void update() {
        Iterator varl = GetAllInBattleInstances.get(uuid).iterator();

        while (varl.hasNext()) {
            AbstractCard c = (AbstractCard)varl.next();
            c.magicNumber += amount;
        }

        isDone = true;
    }
}
