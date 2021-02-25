package cards;

import basemod.abstracts.CustomCard;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import mod.RitualistMod;
import patches.MainEnum;
import powers.SymbiotePower;

import javax.smartcardio.Card;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Symbiote extends CustomCard {
    /*
    * RARE Power
    * 1E
    * Summons refund half their attunement cost and don't exhaust.
     */

    //Text Declaration

    public static final String ID = RitualistMod.makeID("Symbiote");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = RitualistMod.makePath("customImages/profane.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    public static final Logger logger = LogManager.getLogger(RitualistMod.class.getName());


    // /Text Declaration/
    //Stat Declaration

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = MainEnum.Magenta;

    private static final int COST = 1;
    private static final int MAGIC = 2;
    private static final int UPG = 1;
    private UUID cardID;
    private String cardName = "Summon ";
    private AbstractCard temp;
    public static final String SELECT_TEXT = "Select a Summon to form a bond with.";


    // /Stat Declaration/


    public Symbiote() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = MAGIC;
        magicNumber = baseMagicNumber;

    }

    private void getCard(AbstractCard c)
    {
        if(c != null){
            cardID = c.uuid;
            cardName = c.name;
        }
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        Consumer<List<AbstractCard>> callback = list ->     {
            list.forEach(c -> {
                if(c != null)
                    addToBot(new ApplyPowerAction(p, p, new SymbiotePower(p, c.uuid, c.name)));
            });

        };
        ArrayList<AbstractCard> myGroup = new ArrayList<>();
        myGroup.addAll(0, p.discardPile.group);
        myGroup.addAll(0, p.drawPile.group);
        myGroup.addAll(0, p.hand.group);
        addToBot( new SelectCardsAction(myGroup, 1, SELECT_TEXT, true, c -> c.hasTag(MainEnum.SUMMON_CARD), callback));

        //logger.info("name: " + cardName);
        //logger.info("uuid: " + cardID);

      // addToBot(new ApplyPowerAction(p, p, new SymbiotePower(p, cardID, cardName)));
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new Symbiote();
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPG);
            isInnate=true;
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

}
