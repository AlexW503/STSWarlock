package powers;


import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.DiscardToHandAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import mod.RitualistMod;

import java.util.Iterator;

import static patches.MainEnum.RITUAL_CARD;

public class ApplyTwistedPower extends AbstractPower {

    public static final String POWER_ID = RitualistMod.makeID("ApplyTwistedPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = RitualistMod.makePath("customImages/buffTest.png");

    public ApplyTwistedPower(final AbstractCreature owner) {
        name = NAME;
        ID = POWER_ID;
        this.owner = owner;
        amount = -1;
        description = DESCRIPTIONS[0];
        type = PowerType.BUFF;
        isTurnBased = false;
        img = new Texture(IMG);
       // source = source;

    }

    @Override
    public void atStartOfTurn() { //Remove this power and add Twisted Ecstasy

        addToBot
                (new ApplyPowerAction(owner, owner, new TwistedPower(owner)));
        addToBot
                (new RemoveSpecificPowerAction(owner, owner, POWER_ID));


    }



}
