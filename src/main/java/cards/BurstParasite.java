package cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.ShockWaveEffect;
import mod.RitualistMod;
import patches.MainEnum;
import powers.BurstParasitePower;
import powers.PossessionPower;

public class BurstParasite extends CustomCard {

    //Text Declaration

    public static final String ID = RitualistMod.makeID("BurstParasite");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = RitualistMod.makePath("customImages/burst.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;


    // /Text Declaration/
    //Stat Declaration

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = MainEnum.Magenta;

    private static final int COST = 1;
    private static final int UPGRADE_MAG = 3;
    private static final int MAGIC = 9;
    private static final int POS = 2;

    // /Stat Declaration/


    public BurstParasite() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = MAGIC;
        magicNumber = baseMagicNumber;
        exhaust = true;
    }
    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (Settings.FAST_MODE) {
            addToBot(new VFXAction(p, new ShockWaveEffect(p.hb.cX, p.hb.cY, Settings.GREEN_TEXT_COLOR, ShockWaveEffect.ShockWaveType.CHAOTIC), 0.3F));
        } else {
            addToBot(new VFXAction(p, new ShockWaveEffect(p.hb.cX, p.hb.cY, Settings.GREEN_TEXT_COLOR, ShockWaveEffect.ShockWaveType.CHAOTIC), 1.5F));
        }

        /*
        Iterator var3 = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();
        //apply debuffs to all enemies
        AbstractMonster mo;
        while(var3.hasNext()) {
            mo = (AbstractMonster)var3.next();
            addToBot(new ApplyPowerAction(mo, p, new PossessionPower(mo, p, magicNumber), magicNumber));
        }*/

        //apply to target then apply to all, N*2 and N
       // addToBot(new ApplyPowerAction(m, p, new PossessionPower(m, p, POS), POS));
        addToBot(new ApplyPowerAction(m, p, new BurstParasitePower(m, magicNumber), magicNumber));



    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new BurstParasite();
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_MAG);
            initializeDescription();
        }
    }

}
