package relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import mod.RitualistMod;
import powers.AttunePower;


public class StarterRelic extends CustomRelic {

    // ID, images, text.
    public static final String ID = RitualistMod.makeID("StarterRelic");
    public static final String IMG = RitualistMod.makePath("customImages/bookOfIncantations.png");
    public static final String OUTLINE = RitualistMod.makePath(RitualistMod.BASE_RELIC_OUTLINE);
    boolean turnCount = true;

    public StarterRelic() {
        super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.STARTER, LandingSound.MAGICAL);

    }

    // Flash and add stack at turn start
    @Override
    public void atTurnStart() {
        flash();
        //draw one card every other turn
        if(turnCount == true) {
            AbstractDungeon.actionManager.addToTop(new DrawCardAction(AbstractDungeon.player, 1));
            turnCount = false;
        }
        else
            turnCount = true;
    }

    //Description
    @Override
    public String getUpdatedDescription() { return DESCRIPTIONS[0]; }

    //Which relic to return when copying this relic.
    @Override
    public AbstractRelic makeCopy() {
        return new StarterRelic();
    }

}
