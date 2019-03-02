package mod;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.CardStrings;

import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import basemod.BaseMod;
import basemod.ModLabel;
import basemod.ModPanel;
import basemod.interfaces.EditCardsSubscriber;
import basemod.interfaces.EditCharactersSubscriber;
import basemod.interfaces.EditKeywordsSubscriber;
import basemod.interfaces.EditRelicsSubscriber;
import basemod.interfaces.EditStringsSubscriber;
import basemod.interfaces.PostInitializeSubscriber;

import patches.*;
import powers.*;
import relics.*;
import characters.*;
import variables.*;
import cards.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Map;

import static patches.MainEnum.RITUAL_CARD;


@SpireInitializer
public class RitualistMod implements EditCardsSubscriber, EditRelicsSubscriber, EditStringsSubscriber, EditKeywordsSubscriber,
        EditCharactersSubscriber, PostInitializeSubscriber {
    public static final Logger logger = LogManager.getLogger(RitualistMod.class.getName());

    //Mod settings panel
    private static final String MODNAME = "The Ritualist";
    private static final String AUTHOR = "Unbeared";
    private static final String DESCRIPTION = "Unlocks the sinister Ritualist character and their dark forces";

    //Textures and images
        //Character Color
        public static final Color PURPLE = CardHelper.getColor(52.0f, 4.0f, 142.0f);

        //Image folder to not have to change everywhere
        private static final String MOD_ASSETS_FOLDER = "v_images";

        //Card Backgrounds -- currently copied from defaultmod, some from base
        private static final String ATTACK_DEFAULT_GRAY = "cardui/512/bg_attack_gray.png";
        private static final String POWER_DEFAULT_GRAY = "cardui/512/bg_power_gray.png";
        private static final String SKILL_DEFAULT_GRAY = "cardui/512/bg_skill_gray.png";
        private static final String ENERGY_ORB_DEFAULT_GRAY = "cardui/512/card_default_gray_orb.png";
        private static final String CARD_ENERGY_ORB = "cardui/512/card_small_orb.png";

        private static final String ATTACK_DEFAULT_GRAY_PORTRAIT = "cardui/1024/bg_attack_colorless.png";
        private static final String POWER_DEFAULT_GRAY_PORTRAIT = "cardui/1024/bg_power_colorless.png";
        private static final String SKILL_DEFAULT_GRAY_PORTRAIT = "cardui/1024/bg_skill_colorless.png";
        private static final String ENERGY_ORB_DEFAULT_GRAY_PORTRAIT = "cardui/1024/card_default_gray_orb.png";

        //Relic Images
        public static final String BASE_RELIC_OUTLINE = "relics/outline/placeholder_relic.png";


        // Character assets
        private static final String RIT_BUTTON = "customImages/ritButton.png";
        private static final String RIT_PORTRAIT = "customImages/ritPort.png";
        public static final String RIT_SHOULDER_1 = "characters/theSilent/shoulder.png";
        public static final String RIT_SHOULDER_2 = "characters/theSilent/shoulder2.png";
        public static final String RIT_CORPSE = "characters/theSilent/corpse.png";
        public static final String RIT_ATLAS = "characters/theSilent/idle/skeleton.atlas";
        public static final String RIT_JSON = "characters/theSilent/idle/skeleton.json";

        public static final String BADGE_IMAGE = "characters/theDefault/Badge.png";
        //Method that combines base folder with the individual strings
        public static final String makePath(String resource) {
         return MOD_ASSETS_FOLDER + "/" + resource; }

      //Subscribe, create color, and initialize

        ArrayList<AbstractCard> cardPool = new ArrayList<>();

    public RitualistMod() {
        logger.info("Subscribe to BaseMod Hooks");
        BaseMod.subscribe(this);
        logger.info("Done subscribing");
        logger.info("Creating the color " + MainEnum.PURPLE.toString());

        BaseMod.addColor(MainEnum.PURPLE, PURPLE, PURPLE, PURPLE,
                PURPLE, PURPLE, PURPLE, PURPLE, makePath(ATTACK_DEFAULT_GRAY),
                makePath(SKILL_DEFAULT_GRAY), makePath(POWER_DEFAULT_GRAY),
                makePath(ENERGY_ORB_DEFAULT_GRAY), makePath(ATTACK_DEFAULT_GRAY_PORTRAIT),
                makePath(SKILL_DEFAULT_GRAY_PORTRAIT), makePath(POWER_DEFAULT_GRAY_PORTRAIT),
                makePath(ENERGY_ORB_DEFAULT_GRAY_PORTRAIT), makePath(CARD_ENERGY_ORB));

        logger.info("Done creating the color");
    }
    @SuppressWarnings("unused")
    public static void initialize() {
        logger.info("========================= Initializing Ritualist Mod. Hi. =========================");
        RitualistMod ritualistMod= new RitualistMod();
        logger.info("========================= /Default/Rit Mod Initialized/ =========================");
    }

    // ============== /SUBSCRIBE, CREATE THE COLOR, INITIALIZE/ =================

    // =============== LOAD THE CHARACTER =================

    @Override
    public void receiveEditCharacters() {
            logger.info("Beginning to edit characters. " + "Add " + MainEnum.RITUALIST.toString());

            BaseMod.addCharacter(new Ritualist("the Ritualist", MainEnum.RITUALIST),
                    makePath(RIT_BUTTON),  makePath(RIT_PORTRAIT), MainEnum.RITUALIST);
        logger.info("Done editing characters");
    }

    // =============== /LOAD THE CHARACTER/ =================

    // =============== POST-INITIALIZE =================


    @Override
    public void receivePostInitialize(){
        logger.info("Loading badge image and mod options");
        // Load the Mod Badge
        Texture badgeTexture = new Texture(makePath(BADGE_IMAGE));

        //Create mod menu
        ModPanel settingsPanel = new ModPanel();
        settingsPanel.addUIElement(new ModLabel("No settings yet!", 400.0f, 700.0f,
                settingsPanel, (me) -> {
            }));
        BaseMod.registerModBadge(badgeTexture, MODNAME, AUTHOR, DESCRIPTION, settingsPanel);

        logger.info("Done loading badge Image and mod options");
    }

    // =============== / POST-INITIALIZE/ =================

    // ================ ADD RELICS ===================

    @Override
    public void receiveEditRelics() {
        logger.info("Adding relics");

        //Color specific relic
        BaseMod.addRelicToCustomPool(new StarterRelic(), MainEnum.PURPLE);

        logger.info("Done adding relics");

    }
    // ================ /ADD RELICS/ ===================

    // ================ ADD CARDS ===================

    @Override
    public void receiveEditCards() {
        logger.info("Adding variables");
        // Add the Custom Dynamic Variables
        BaseMod.addDynamicVariable(new DamageByE_Val());
        BaseMod.addDynamicVariable(new Attune_Val());
        BaseMod.addDynamicVariable(new AttuneMagic_Val());
        BaseMod.addDynamicVariable(new MagicPlus2());

        logger.info("Adding cards");
        //Add the cards
        BaseMod.addCard(new DefendPurple());
        BaseMod.addCard(new StrikePurple());
        BaseMod.addCard(new DefendRitual());
        BaseMod.addCard(new StrikeRitual());
        BaseMod.addCard(new PrepareHost());
        BaseMod.addCard(new DarkRitual());
        BaseMod.addCard(new SummonAgiel());
        BaseMod.addCard(new ScaryShank());
        BaseMod.addCard(new RitualBloodletter());
        BaseMod.addCard(new BarrierOfBone());
        BaseMod.addCard(new EmptyPalms());
        BaseMod.addCard(new QuickStudy());
        BaseMod.addCard(new ImpFeast());
        BaseMod.addCard(new CostlyPreparation());
        BaseMod.addCard(new TemporalRites());
        BaseMod.addCard(new ScoutAhead());
        BaseMod.addCard(new BranchingPaths());
        BaseMod.addCard(new SoulOpening());
        BaseMod.addCard(new TwistedEcstasy());
        BaseMod.addCard(new MassDrainLife());
        BaseMod.addCard(new InitiationRite());
        BaseMod.addCard(new PullFromBeyond());
        BaseMod.addCard(new ThoughtfulArt());
        BaseMod.addCard(new PowerPact());
        BaseMod.addCard(new DeadlyVelocity());
        BaseMod.addCard(new DemonArm());
        BaseMod.addCard(new WinningGambit());
        BaseMod.addCard(new LongCon());
        BaseMod.addCard(new Retreat());
        BaseMod.addCard(new DrainStrength());
        BaseMod.addCard(new CleanseByBlood());
        BaseMod.addCard(new GrowingRites());
        BaseMod.addCard(new DuskSeeker());
        BaseMod.addCard(new Arrival());
        BaseMod.addCard(new TwinWave());
        BaseMod.addCard(new StrategicCut());
        BaseMod.addCard(new SpreadingCurse());
        BaseMod.addCard(new SummonBaphomet());
        BaseMod.addCard(new TransientGhost());
        BaseMod.addCard(new Impale());
        BaseMod.addCard(new DeadlyBlows());
        BaseMod.addCard(new EvilIntentions());
        BaseMod.addCard(new BloodBoil());
        BaseMod.addCard(new Momentum());
        BaseMod.addCard(new CostlyStrength());
        BaseMod.addCard(new ShallowCut());
        BaseMod.addCard(new SummonRerek());
        BaseMod.addCard(new QuietMeditation());
        BaseMod.addCard(new RudeInterruption());
        BaseMod.addCard(new IndiscriminateKnifing());
        BaseMod.addCard(new EarlyReturn());
        BaseMod.addCard(new Sabbath());
        BaseMod.addCard(new DeepReserves());
        BaseMod.addCard(new SummonMaat());
        BaseMod.addCard(new HeavyInfection());
        BaseMod.addCard(new SoulBurst());
        BaseMod.addCard(new RotInPieces());
        BaseMod.addCard(new SymbioteForm());
        BaseMod.addCard(new Extradition());
        BaseMod.addCard(new FuturePlans());
        BaseMod.addCard(new DevilWill());

        logger.info("Unlocking cards");
        //Unlock the cards
        UnlockTracker.unlockCard(DefendPurple.ID);
        UnlockTracker.unlockCard(StrikePurple.ID);
        UnlockTracker.unlockCard(DefendRitual.ID);
        UnlockTracker.unlockCard(StrikeRitual.ID);
        UnlockTracker.unlockCard(PrepareHost.ID);
        UnlockTracker.unlockCard(DarkRitual.ID);
        UnlockTracker.unlockCard(SummonAgiel.ID);
        UnlockTracker.unlockCard(ScaryShank.ID);
        UnlockTracker.unlockCard(RitualBloodletter.ID);
        UnlockTracker.unlockCard(BarrierOfBone.ID);
        UnlockTracker.unlockCard(EmptyPalms.ID);
        UnlockTracker.unlockCard(QuickStudy.ID);
        UnlockTracker.unlockCard(ImpFeast.ID);
        UnlockTracker.unlockCard(CostlyPreparation.ID);
        UnlockTracker.unlockCard(TemporalRites.ID);
        UnlockTracker.unlockCard(ScoutAhead.ID);
        UnlockTracker.unlockCard(BranchingPaths.ID);
        UnlockTracker.unlockCard(SoulOpening.ID);
        UnlockTracker.unlockCard(TwistedEcstasy.ID);
        UnlockTracker.unlockCard(MassDrainLife.ID);
        UnlockTracker.unlockCard(InitiationRite.ID);
        UnlockTracker.unlockCard(PullFromBeyond.ID);
        UnlockTracker.unlockCard(ThoughtfulArt.ID);
        UnlockTracker.unlockCard(PowerPact.ID);
        UnlockTracker.unlockCard(DeadlyVelocity.ID);
        UnlockTracker.unlockCard(DemonArm.ID);
        UnlockTracker.unlockCard(WinningGambit.ID);
        UnlockTracker.unlockCard(LongCon.ID);
        UnlockTracker.unlockCard(Retreat.ID);
        UnlockTracker.unlockCard(DrainStrength.ID);
        UnlockTracker.unlockCard(CleanseByBlood.ID);
        UnlockTracker.unlockCard(GrowingRites.ID);
        UnlockTracker.unlockCard(DuskSeeker.ID);
        UnlockTracker.unlockCard(Arrival.ID);
        UnlockTracker.unlockCard(TwinWave.ID);
        UnlockTracker.unlockCard(StrategicCut.ID);
        UnlockTracker.unlockCard(SpreadingCurse.ID);
        UnlockTracker.unlockCard(SummonBaphomet.ID);
        UnlockTracker.unlockCard(TransientGhost.ID);
        UnlockTracker.unlockCard(Impale.ID);
        UnlockTracker.unlockCard(DeadlyBlows.ID);
        UnlockTracker.unlockCard(EvilIntentions.ID);
        UnlockTracker.unlockCard(BloodBoil.ID);
        UnlockTracker.unlockCard(Momentum.ID);
        UnlockTracker.unlockCard(CostlyStrength.ID);
        UnlockTracker.unlockCard(ShallowCut.ID);
        UnlockTracker.unlockCard(SummonRerek.ID);
        UnlockTracker.unlockCard(QuietMeditation.ID);
        UnlockTracker.unlockCard(RudeInterruption.ID);
        UnlockTracker.unlockCard(IndiscriminateKnifing.ID);
        UnlockTracker.unlockCard(EarlyReturn.ID);
        UnlockTracker.unlockCard(Sabbath.ID);
        UnlockTracker.unlockCard(DeepReserves.ID);
        UnlockTracker.unlockCard(SummonMaat.ID);
        UnlockTracker.unlockCard(SoulBurst.ID);
        UnlockTracker.unlockCard(RotInPieces.ID);
        UnlockTracker.unlockCard(HeavyInfection.ID);
        UnlockTracker.unlockCard(SymbioteForm.ID);
        UnlockTracker.unlockCard(Extradition.ID);
        UnlockTracker.unlockCard(FuturePlans.ID);
        UnlockTracker.unlockCard(DevilWill.ID);

        logger.info("Done adding cards");
    }
    // ================ /ADD CARDS/ ===================

    // ================ LOAD THE TEXT ===================

    @Override
    public void receiveEditStrings() {
            logger.info("Beginning to edit strings");

            //CardStrings
            BaseMod.loadCustomStringsFile(CardStrings.class,
                    "localization/Ritualist_Card_Strings.json");
            BaseMod.loadCustomStringsFile(PowerStrings.class,
                    "localization/Ritualist_Power_Strings.json");
            BaseMod.loadCustomStringsFile(RelicStrings.class,
                    "localization/Ritualist_Relic_Strings.json");
            BaseMod.loadCustomStringsFile(OrbStrings.class,
                    "localization/Ritualist_Orb_Strings.json");


            logger.info("Done editing strings");
    }
    // ================ /LOAD THE TEXT/ ===================

    // ================ LOAD THE KEYWORDS ===================
    //Keywords make yellow highlight text with pop-up box
    @Override
    public void receiveEditKeywords() {
            final String[] RitualKW = { "ritual", "rituals", "Ritual", "Rituals", "Ritual." };
            BaseMod.addKeyword("warlock", "Ritual", RitualKW, "Whenever you play a ritual, gain 1 Attunement. Attunement increases the effects of Summons.");
            final String[] PossessKW = { "possess", "possession", "Possession", "possessed" };
            BaseMod.addKeyword("warlock", "Possess", PossessKW, "At the start of turn, take damage equal to your possession.");
            final String[] AttuneKW = { "attune", "Attune", "Attunement", "attunement" };
            BaseMod.addKeyword("warlock", "Attune", AttuneKW, "Increase the effects of Summons.");
            final String[] SummonKW = { "Summon:", "Summon", "summon"};
            BaseMod.addKeyword("warlock", "Summon", SummonKW, "Fills an orb slot. Evoked by Banish cards or other summons. Start with 1 Summon Slot");
            final String[] BanishKW = { "Banish", "Banishment", "banish"};
            BaseMod.addKeyword("warlock", "Banish", BanishKW, "Removes a summoned demon from it's orb slot");

    }
    // ================ /LOAD THE KEYWORDS/ ===================


    // this adds "Ritualist:" before the ID of any card/relic/power etc.
     // in order to avoid conflicts if any other mod uses the same ID.
    public static String makeID(String idText) {
            return "Ritualist:" + idText; }


    //Used by ritual cards to apply a stack of attunement
    //Previously if only there is no Attune, add one stack

    public static void AttuneAdd() {
            //if(!AbstractDungeon.player.hasPower(AttunePower.POWER_ID)) {
              //  logger.info("No Attune found");
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new AttunePower(AbstractDungeon.player, 1), 1));
        }

    public ArrayList getPool()
    {
        if(cardPool.size() == 0) {
            AbstractCard card;
            for (Map.Entry<String, AbstractCard> c : CardLibrary.cards.entrySet()) {
                card = c.getValue();
                AbstractCard.CardColor color = AbstractDungeon.player.getCardColor();
                if (card.color.equals(color) && card.rarity != AbstractCard.CardRarity.BASIC && card.hasTag(RITUAL_CARD) && (!UnlockTracker.isCardLocked(c.getKey()))) {
                    cardPool.add(card);
                }
            }
        }
        return cardPool;
    }


}

