package actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.actions.defect.EvokeOrbAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;
import com.megacrit.cardcrawl.random.Random;
import orbs.*;

import java.util.Collections;

public class RepatriateAction extends AbstractGameAction
{
    //private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("ExhaustAction");
   // public static final String TEXT = "Select a card to add to your deck.";
    private AbstractPlayer p;
    private AbstractMonster mo;
    private AbstractOrb orb;


    public RepatriateAction(int amount)
    {
        this.p = AbstractDungeon.player;
        this.amount = amount;
        this.duration = (this.startDuration = Settings.ACTION_DUR_FAST);
        this.actionType = ActionType.SPECIAL;
    }


    public void update()
    {
        int i;
        int orbAttune;

        if (!AbstractDungeon.player.orbs.isEmpty()) {
            i = AbstractDungeon.cardRandomRng.random(4);
            orbAttune = AbstractDungeon.player.orbs.get(0).evokeAmount;

            this.orb = AbstractDungeon.player.orbs.get(0);
            if (this.orb instanceof EmptyOrbSlot) {
                this.isDone = true;
            } else {

                switch(i) {
                    case 0:
                        this.addToTop(new ChannelAction(new AgielOrb(orbAttune)));
                    case 1:
                        this.addToTop(new ChannelAction(new RerekOrb(orbAttune)));
                    case 2:
                        this.addToTop(new ChannelAction(new MaatOrb(orbAttune)));
                    case 3:
                        this.addToTop(new ChannelAction(new NergalOrb(orbAttune)));

                }
                this.addToTop(new BanishAction(amount));


            }
        }

        isDone = true;

        tickDuration();
    }
}
