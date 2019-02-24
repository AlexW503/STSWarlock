package relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import basemod.abstracts.CustomRelic;
import mod.RitualistMod;


public class EnergyExampleRelic extends CustomRelic {

    // ID, images, text.
    public static final String ID = mod.RitualistMod.makeID("EnergyExampleRelic");
    public static final String IMG = RitualistMod.makePath("relic/placeholder_relic.png");
    public static final String OUTLINE = RitualistMod.makePath(RitualistMod.BASE_RELIC_OUTLINE);

    public EnergyExampleRelic() {
        super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.STARTER, LandingSound.MAGICAL);

    }

    // Flash at the start of Battle.
    @Override
    public void atBattleStartPreDraw() {
        flash();
    }

    // Gain 1 energy on equip.
    @Override
    public void onEquip() {
        AbstractDungeon.player.energy.energyMaster += 1;
    }

    // Lose 1 energy on unequip.
    @Override
    public void onUnequip() {
        AbstractDungeon.player.energy.energyMaster -= 1;
    }

    //Description
    @Override
    public String getUpdatedDescription() { return DESCRIPTIONS[0]; }

    //Which relic to return when copying this relic.
    @Override
    public AbstractRelic makeCopy() {
        return new EnergyExampleRelic();
    }

}
