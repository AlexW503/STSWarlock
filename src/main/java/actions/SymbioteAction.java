package actions;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import patches.MainEnum;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

public class SymbioteAction extends AbstractGameAction {

    public static final String SELECT_TEXT = "Select a Summon to form a bond with.";
    private UUID id;

    public SymbioteAction() {
        duration = 0.0F;
        actionType = ActionType.WAIT;
    }

    public void update() {
        AbstractPlayer p = AbstractDungeon.player;

        Consumer<List<AbstractCard>> callback = list ->
        {
            list.forEach(c -> c.uuid = id);

        };
        ArrayList<AbstractCard> myGroup = new ArrayList<>();
        myGroup.addAll(0, p.discardPile.group);
        myGroup.addAll(0, p.drawPile.group);
        myGroup.addAll(0, p.hand.group);



        new SelectCardsAction(myGroup, 1, SELECT_TEXT, true, c -> c.hasTag(MainEnum.SUMMON_CARD), callback);

        isDone = true;
    }
}
