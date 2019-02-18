package characters;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import basemod.abstracts.CustomPlayer;
import mod.RitualistMod;
import cards.*;
import patches.*;
import powers.*;
import relics.*;

public class Ritualist extends CustomPlayer{
    public static final Logger logger = LogManager.getLogger(RitualistMod.class.getName());

    // =============== BASE STATS =================

    public static final int ENERGY_PER_TURN = 3;
    public static final int STARTING_HP = 70;
    public static final int MAX_HP = 70;
    public static final int STARTING_GOLD = 99;
    public static final int CARD_DRAW = 5;
    public static final int ORB_SLOTS = 1;

    // =============== /BASE STATS/ =================

    // Big Energy Texture
     public static final String[] orbTextures = {
             "v_images/ui/topPanel/blue/1.png",
            "v_images/ui/topPanel/blue/1d.png",
            "v_images/ui/topPanel/blue/2.png",
            "v_images/ui/topPanel/blue/2d.png",
            "v_images/ui/topPanel/blue/3.png",
            "v_images/ui/topPanel/blue/3d.png",
            "v_images/ui/topPanel/blue/4.png",
            "v_images/ui/topPanel/blue/4d.png",
            "v_images/ui/topPanel/blue/5.png",
            "v_images/ui/topPanel/blue/5d.png",
            "v_images/ui/topPanel/blue/border.png", };
     // /Big Energy Texture/

    //=============== CHARACTER CLASS START =================
    public Ritualist(String name, PlayerClass setClass) {
        super(name, setClass, orbTextures,
                "v_images/ui/topPanel/energyGreenVFX.png", null,
                null, null);


        //=============== TEXTURES, ENERGY, LOADOUT =================

        initializeClass(null, // required call to load textures and setup energy/loadout
                RitualistMod.makePath(RitualistMod.RIT_SHOULDER_1), // campfire pose
                RitualistMod.makePath(RitualistMod.RIT_SHOULDER_2), // another campfire pose
                RitualistMod.makePath(RitualistMod.RIT_CORPSE), // dead corpse
                getLoadout(), 20.0F, -10.0F, 220.0F, 290.0F, new EnergyManager(ENERGY_PER_TURN)); // energy manager

        // =============== /TEXTURES, ENERGY, LOADOUT/ =================
        // === ============ ANIMATIONS =================

        loadAnimation(
                mod.RitualistMod.makePath(RitualistMod.RIT_ATLAS),
                mod.RitualistMod.makePath(RitualistMod.RIT_JSON),
                1.0f);
        AnimationState.TrackEntry e = state.setAnimation(0, "Idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());

        // =============== /ANIMATIONS/ =================
        // =============== TEXT BUBBLE LOCATION =================

        dialogX = (drawX + 0.0F * Settings.scale); // set location for text bubbles
        dialogY = (drawY + 220.0F * Settings.scale); // you can just copy these values

        // =============== /TEXT BUBBLE LOCATION/ =================

}
    // =============== /CHARACTER CLASS END/ =================

    //Starting description/loadout
    @Override
    public CharSelectInfo getLoadout() {
        return new CharSelectInfo("Warlock", "The sinister Warlock. NL Dark arts, possession, and demon summoning.",
                STARTING_HP, MAX_HP, ORB_SLOTS, STARTING_GOLD, CARD_DRAW, this, getStartingRelics(),
                getStartingDeck(), false);

    }
    //Starting deck
    @Override
    public ArrayList<String> getStartingDeck() {
        ArrayList<String> retVal = new ArrayList<>();

        logger.info("Loading starter deck strings");


        retVal.add(StrikePurple.ID);
        retVal.add(StrikePurple.ID);
        retVal.add(StrikePurple.ID);
        retVal.add(StrikeRitual.ID);
        retVal.add(DefendPurple.ID);
        retVal.add(DefendPurple.ID);
        retVal.add(DefendPurple.ID);
        retVal.add(DefendRitual.ID);
        retVal.add(InitiationRite.ID);
        retVal.add(SummonAgiel.ID);



        return retVal;
        }

    //Starting Relics
    public ArrayList<String> getStartingRelics() {
        ArrayList<String> retVal = new ArrayList<>();

        retVal.add(StarterRelic.ID);
        UnlockTracker.markRelicAsSeen(StarterRelic.ID);

        return retVal;
    }

    //Character Select Screen Effect
    @Override
    public void doCharSelectScreenSelectEffect() {
        CardCrawlGame.sound.playA("ATTACK_DAGGER_1", 1.25f); // Sound Effect
        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.LOW, ScreenShake.ShakeDur.SHORT,
                false); // Screen Effect
    }

    // Character Select on-button-press sound effect
    @Override
    public String getCustomModeCharacterButtonSoundKey() { return "ATTACK_DAGGER_1"; }

    // Should return how much HP your maximum HP reduces by when starting a run at
    // Ascension 14 or higher. (ironclad loses 5, defect and silent lose 4 hp respectively)
    @Override
    public int getAscensionMaxHPLoss() {
        return 4;
    }

    // Should return the card color enum to be associated with your character.
    @Override
    public AbstractCard.CardColor getCardColor() {
        return MainEnum.PURPLE;
    }

    // Should return a color object to be used to color the trail of moving cards
    @Override
    public Color getCardTrailColor() {
        return RitualistMod.PURPLE;
    }

    // Should return a BitmapFont object that you can use to customize how your
    // energy is displayed from within the energy orb.
    @Override
    public BitmapFont getEnergyNumFont() {
        return FontHelper.energyNumFontGreen;
    }

    // Should return class name as it appears in run history screen.
    @Override
    public String getLocalizedCharacterName() {
        return "Warlock";
    }

    //Which card should be obtainable from the Match and Keep event? *CHANGE*
    @Override
    public AbstractCard getStartCardForEvent() {
        return new StrikePurple();
    }

    // The class name as it appears next to your player name in-game
    @Override
    public String getTitle(AbstractPlayer.PlayerClass playerClass) {
        return "the Warlock";
    }

    // Should return a new instance of your character, sending name as its name parameter.
    @Override
    public AbstractPlayer newInstance() {
        return new Ritualist(name, chosenClass);
    }

    // Should return a Color object to be used to color the miniature card images in run history.
    @Override
    public Color getCardRenderColor() {
        return RitualistMod.PURPLE;
    }

    // Should return a Color object to be used as screen tint effect when your
    // character attacks the heart.
    @Override
    public Color getSlashAttackColor() {
        return RitualistMod.PURPLE;
    }

    // Should return an AttackEffect array of any size greater than 0. These effects
    // will be played in sequence as your character's finishing combo on the heart.
    // Attack effects are the same as used in DamageAction and the like.
    @Override
    public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect() {
        return new AbstractGameAction.AttackEffect[] {
                AbstractGameAction.AttackEffect.BLUNT_HEAVY,
                AbstractGameAction.AttackEffect.BLUNT_HEAVY,
                AbstractGameAction.AttackEffect.BLUNT_HEAVY};
    }

    // Should return a string containing what text is shown when your character is
    // about to attack the heart. For example, the defect is "NL You charge your
    // core to its maximum..."
    @Override
    public String getSpireHeartText() {
        return "NL You summon your demons...";
    }

    // The vampire events refer to the base game characters as "brother", "sister",
    // and "broken one" respectively.This method should return a String containing
    // the full text that will be displayed as the first screen of the vampires event.
    @Override
    public String getVampireText() {
        return "Navigating an unlit street, you come across several hooded figures in the midst of some dark ritual. As you approach, they turn to you in eerie unison. " +
                "The tallest among them approaches you with a solemn countenance. NL ~\"The~ ~taint~ ~is~ ~strong~ ~on~ ~you,~ ~but~ ~we~ ~will~ ~trade~ ~all~ ~the~ ~same~.\"~";
    }

    @Override
    public void applyStartOfCombatLogic() {
        super.applyStartOfCombatLogic();
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new AttunePower(AbstractDungeon.player, 0), 0));
    }



}
