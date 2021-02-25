package relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import mod.RitualistMod;


public class FeedingMirror extends CustomRelic {

    // ID, images, text.
    public static final String ID = RitualistMod.makeID("FeedingMirror");
    public static final String IMG = RitualistMod.makePath("customImages/feedingMirror.png");
    public static final String OUTLINE = RitualistMod.makePath(RitualistMod.BASE_RELIC_OUTLINE);
    public static final int amount = 4;

    public FeedingMirror() {
        super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.RARE, LandingSound.MAGICAL);

    }


    @Override
    public void onPlayerEndTurn() {
        if(EnergyPanel.totalCount > 0) {
            int block;
            block = EnergyPanel.totalCount;
            block *= amount;
            addToBot(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, block));
            EnergyPanel.totalCount = 0;
        }
    }

    //Description
    @Override
    public String getUpdatedDescription() { return DESCRIPTIONS[0]; }

    //Which relic to return when copying this relic.
    @Override
    public AbstractRelic makeCopy() {
        return new FeedingMirror();
    }

}
