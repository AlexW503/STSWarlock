package powers;


import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.HealthBarRenderPower;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.common.*;

import com.megacrit.cardcrawl.rooms.AbstractRoom;
import mod.RitualistMod;

import static javafx.scene.paint.Color.GREY;

public class PossessionPower extends AbstractPower implements HealthBarRenderPower {
    public AbstractCreature source;

    public static final String POWER_ID = RitualistMod.makeID("PossessionPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESC = powerStrings.DESCRIPTIONS;
    public static final String IMG = RitualistMod.makePath("customImages/debuffPower.png");
    Color healthColor;

    public PossessionPower(final AbstractCreature owner, AbstractCreature source, final int amount) {
        name = NAME;
        ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        updateDescription();
        type = PowerType.DEBUFF;
        isTurnBased = false;
        img = new Texture(IMG);
        this.source = source;



    }


    @Override
    public int getHealthBarAmount()
    {
        return amount;
    }

    @Override
    public Color getColor()
    {
        return healthColor.GRAY;
    }


    @Override 
    public void updateDescription() {
        description = DESC[0] + amount + DESC[1];
    }

    @Override
    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
    }

    @Override
    public void atStartOfTurn() {
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT && !AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            flashWithoutSound();
            addToBot(new LoseHPAction(owner, source, amount, AbstractGameAction.AttackEffect.POISON ));
        }
    }

}
