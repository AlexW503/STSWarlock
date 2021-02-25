package relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import mod.RitualistMod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import powers.AttunePower;


public class MortalTether extends CustomRelic {

    // ID, images, text.
    public static final String ID = RitualistMod.makeID("MortalTether");
    public static final String IMG = RitualistMod.makePath("customImages/mortal.png");
    public static final String OUTLINE = RitualistMod.makePath(RitualistMod.BASE_RELIC_OUTLINE);
    public static final int amount = 10;
    public static final Logger logger = LogManager.getLogger(RitualistMod.class.getName());


    public MortalTether() {
        super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.BOSS, LandingSound.MAGICAL);

    }

    @Override
    public void atTurnStartPostDraw() {
        AbstractPlayer p = AbstractDungeon.player;


        flash();
        if(p.hasPower(AttunePower.POWER_ID)) {
            int reduce = p.getPower(AttunePower.POWER_ID).amount;
            if(reduce > amount)
                reduce = amount;
           // logger.info("has attune power");
            addToBot(new ApplyPowerAction(p, p, new AttunePower(p, -reduce), -reduce));
        }

    }

    // Gain 1 energy on equip.
    @Override
    public void onEquip() {
        ++AbstractDungeon.player.energy.energyMaster;
    }

    @Override
    public void onUnequip() {
        --AbstractDungeon.player.energy.energyMaster;
    }

    //Description
    @Override
    public String getUpdatedDescription() { return DESCRIPTIONS[0]; }

    //Which relic to return when copying this relic.
    @Override
    public AbstractRelic makeCopy() {
        return new MortalTether();
    }

}
