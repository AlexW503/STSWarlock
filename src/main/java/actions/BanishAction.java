package actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.defect.EvokeOrbAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;
import orbs.AbstractDemonOrb;
import powers.PossessionPower;

import java.util.Collections;

public class BanishAction extends AbstractGameAction
{
    //private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("ExhaustAction");
   // public static final String TEXT = "Select a card to add to your deck.";
    private AbstractPlayer p;
    private AbstractMonster mo;

    public BanishAction(int amount)
    {
        this.p = AbstractDungeon.player;
        this.amount = amount;
        this.duration = (this.startDuration = Settings.ACTION_DUR_FAST);
        this.actionType = ActionType.SPECIAL;
    }


    public void update()
    {
        int i;


        //if (this.duration == this.startDuration)
        //for(i = 0; i < amount; i++) {

                if ((!this.p.orbs.isEmpty()) && (!(this.p.orbs.get(0) instanceof EmptyOrbSlot))) {
                    for(int j = 0; j < amount; j++)
                        ((AbstractDemonOrb) this.p.orbs.get(0)).onBanish();
                    AbstractOrb orbSlot = new EmptyOrbSlot();
                    for (i = 1; i < this.p.orbs.size(); i++) {
                        Collections.swap(this.p.orbs, i, i - 1);
                    }
                    this.p.orbs.set(this.p.orbs.size() - 1, orbSlot);
                    for (i = 0; i < this.p.orbs.size(); i++) {
                        (this.p.orbs.get(i)).setSlot(i, this.p.maxOrbs);
                    }

                }

       // }
        addToTop(new EvokeOrbAction(1));

        isDone = true;

        tickDuration();
    }
}
