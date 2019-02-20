package orbs;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.unique.LoseEnergyAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.vfx.combat.PlasmaOrbActivateEffect;
import mod.RitualistMod;

public class BaphOrb extends AbstractOrb {
    public static final String ORB_ID =  RitualistMod.makeID("BaphOrb");
    private static final OrbStrings orbString = CardCrawlGame.languagePack.getOrbString(ORB_ID);
    public static final String[] DESC = orbString.DESCRIPTION;
    private static final float ORB_BORDER_SCALE = 1.2F;
    private float vfxTimer = 0.5F;
    private static final float VFX_INTERVAL_TIME = 0.25F;

    public BaphOrb(){
        ID = "Baph";
        img = ImageMaster.ORB_DARK;
        name = orbString.NAME;
        baseEvokeAmount = 1;
        evokeAmount = baseEvokeAmount;
        basePassiveAmount = 1;
        passiveAmount = basePassiveAmount;
        updateDescription();
        channelAnimTimer = 0.5F;
    }

    public void updateDescription() {
        applyFocus();
        description = DESC[0] + passiveAmount + DESC[1] + evokeAmount + DESC[2];
    }

    public void onEvoke() {
        AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(evokeAmount));
    }

    public void onStartOfTurn() {
        float speedTime = 0.6F / (float)AbstractDungeon.player.orbs.size();
        if (Settings.FAST_MODE) {
            speedTime = 0.0F;
        }

        AbstractDungeon.actionManager.addToBottom(new LoseEnergyAction(passiveAmount));
        updateDescription();

    }

    public void triggerEvokeAnimation() {
        CardCrawlGame.sound.play("ORB_PLASMA_EVOKE", 0.1F);
        AbstractDungeon.effectsQueue.add(new PlasmaOrbActivateEffect(cX, cY));
    }

    public void render(SpriteBatch sb) {
        sb.setColor(c);
        sb.draw(img, cX - 48.0F, cY - 48.0F + bobEffect.y, 48.0F, 48.0F, 96.0F, 96.0F, scale, scale, angle, 0, 0, 96, 96, false, false);
        sb.setColor(new Color(1.0F, 1.0F, 1.0F, c.a / 3.0F));
        sb.setBlendFunction(770, 1);
        sb.draw(img, cX - 48.0F, cY - 48.0F + bobEffect.y, 48.0F, 48.0F, 96.0F, 96.0F, scale * 1.2F, scale * 1.2F, angle / 1.2F, 0, 0, 96, 96, false, false);
        sb.draw(img, cX - 48.0F, cY - 48.0F + bobEffect.y, 48.0F, 48.0F, 96.0F, 96.0F, scale * 1.5F, scale * 1.5F, angle / 1.4F, 0, 0, 96, 96, false, false);
        sb.setBlendFunction(770, 771);
        renderText(sb);
        hb.render(sb);
    }

    protected void renderText(SpriteBatch sb) {
        FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, Integer.toString(evokeAmount), cX + NUM_X_OFFSET, cY + bobEffect.y / 2.0F + NUM_Y_OFFSET - 4.0F * Settings.scale, new Color(0.2F, 1.0F, 1.0F, c.a), fontScale);
        FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, Integer.toString(passiveAmount), cX + NUM_X_OFFSET, cY + bobEffect.y / 2.0F + NUM_Y_OFFSET + 20.0F * Settings.scale, c, fontScale);
    }

    public void playChannelSFX() {
        CardCrawlGame.sound.play("ORB_DARK_CHANNEL", 0.1F);
    }

    public AbstractOrb makeCopy() {
        return new BaphOrb();
    }
}