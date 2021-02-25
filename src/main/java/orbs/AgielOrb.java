package orbs;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.unique.SwordBoomerangAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.vfx.combat.PlasmaOrbActivateEffect;
import mod.RitualistMod;
import powers.SymbiotePower;

public class AgielOrb extends AbstractDemonOrb {
    public static final String ORB_ID =  RitualistMod.makeID("AgielOrb");
    private static final OrbStrings orbString = CardCrawlGame.languagePack.getOrbString(ORB_ID);
    public static final String[] DESC = orbString.DESCRIPTION;
    private static final float ORB_BORDER_SCALE = 1.2F;
    private float vfxTimer = 0.5F;
    private static final float VFX_INTERVAL_TIME = 0.25F;

    public AgielOrb(int amount){
        ID = "Agiel";
        img = ImageMaster.ORB_DARK;
        name = orbString.NAME;
        baseEvokeAmount = amount;
        evokeAmount = baseEvokeAmount;
        basePassiveAmount = 0;
        passiveAmount = basePassiveAmount;
        updateDescription();
        channelAnimTimer = 0.5F;
    }

    public void updateDescription() {
        applyFocus();
        description = DESC[0] + evokeAmount + DESC[1];
    }


    public void onEvoke() {
        logger.info("evoked " + ID);
      //  if(AbstractDungeon.player.hasPower(SymbiotePower.POWER_ID) && !isBanished)
          //  AbstractDungeon.actionManager.addToBottom(new SwordBoomerangAction(AbstractDungeon.getMonsters().getRandomMonster //Deal dmg to random enemy
               // (null, true, AbstractDungeon.cardRandomRng), new DamageInfo(AbstractDungeon.player, evokeAmount, DamageInfo.DamageType.THORNS), 1));


    }

    public void onBanish() {
        logger.info("banished " + ID);
        AbstractDungeon.actionManager.addToBottom(new SwordBoomerangAction(AbstractDungeon.getMonsters().getRandomMonster
                (null, true, AbstractDungeon.cardRandomRng), new DamageInfo(AbstractDungeon.player, evokeAmount, DamageInfo.DamageType.THORNS), 1));
        isBanished = true;
    }

    public void onStartOfTurn() {
        /*
        float speedTime = 0.6F / (float)AbstractDungeon.player.orbs.size();
        if (Settings.FAST_MODE) {
            speedTime = 0.0F;
        }
        addToBot(new SwordBoomerangAction(AbstractDungeon.getMonsters().getRandomMonster //Deal dmg to random enemy
                (null, true, AbstractDungeon.cardRandomRng), new DamageInfo(AbstractDungeon.player, basePassiveAmount, DamageInfo.DamageType.THORNS), 1));

        updateDescription();
        */
    }

    public void triggerEvokeAnimation() {
        CardCrawlGame.sound.play("ORB_PLASMA_EVOKE", 0.1F);
        AbstractDungeon.effectsQueue.add(new PlasmaOrbActivateEffect(cX, cY));
    }

    public void render(SpriteBatch sb) {
        sb.setColor(c);
        sb.draw(img, cX - 48.0F, cY - 48.0F + bobEffect.y, 48.0F, 48.0F, 96.0F, 96.0F, scale, scale, angle, 0, 0, 96, 96, false, false);
        sb.setColor(new Color(0.1F, 0.1F, 0.1F, c.a / 3.0F));
        sb.setBlendFunction(770, 1);
        sb.draw(img, cX - 48.0F, cY - 48.0F + bobEffect.y, 48.0F, 48.0F, 96.0F, 96.0F, scale * 1.2F, scale * 1.2F, angle / 1.2F, 0, 0, 96, 96, false, false);
        sb.draw(img, cX - 48.0F, cY - 48.0F + bobEffect.y, 48.0F, 48.0F, 96.0F, 96.0F, scale * 1.5F, scale * 1.5F, angle / 1.4F, 0, 0, 96, 96, false, false);
        sb.setBlendFunction(770, 771);
        renderText(sb);
        hb.render(sb);
    }

    protected void renderText(SpriteBatch sb) {
        FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, Integer.toString(evokeAmount), cX + NUM_X_OFFSET, cY + bobEffect.y / 2.0F + NUM_Y_OFFSET + 4.0F * Settings.scale, new Color(0.2F, 1.0F, 1.0F, c.a), fontScale);
       // FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, Integer.toString(passiveAmount), cX + NUM_X_OFFSET, cY + bobEffect.y / 2.0F + NUM_Y_OFFSET + 20.0F * Settings.scale, c, fontScale);
    }

    public void playChannelSFX() {
        CardCrawlGame.sound.play("ORB_DARK_CHANNEL", 0.1F);
    }

    public AbstractOrb makeCopy() {
        return new AgielOrb(baseEvokeAmount);
    }
}
