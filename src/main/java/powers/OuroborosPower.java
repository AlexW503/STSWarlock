package powers;


import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.unique.SwordBoomerangAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import mod.RitualistMod;

public class OuroborosPower extends AbstractPower {

    public static final String POWER_ID = RitualistMod.makeID("OuroborosPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = RitualistMod.makePath("customImages/buffTest.png");
    private boolean blockTurn = true;
    private int block;
    private int damage;

    public OuroborosPower(final AbstractCreature owner, int blockAmount) {
        name = NAME;
        ID = POWER_ID;
        this.owner = owner;
        this.amount = blockAmount;
        block = blockAmount;
       // damage = blockAmount+1;
        updateDescription();
        type = PowerType.BUFF;
        isTurnBased = false;
        //img = new Texture(IMG);
        loadRegion("loop");
       // source = source;

    }
    @Override
    public void updateDescription(){
        if(blockTurn)
            description = DESCRIPTIONS[0] + DESCRIPTIONS[1] + amount + DESCRIPTIONS[2];
        else
            description = DESCRIPTIONS[0] + DESCRIPTIONS[3] + amount + DESCRIPTIONS[4];
    }

    @Override
    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        flash();

        if(blockTurn) {
            addToBot(new GainBlockAction(owner, owner, amount));
            blockTurn = false;

        }

        else {
            AbstractDungeon.actionManager.addToBottom(new SwordBoomerangAction(AbstractDungeon.getMonsters().getRandomMonster
                    (null, true, AbstractDungeon.cardRandomRng), new DamageInfo(AbstractDungeon.player, amount, DamageInfo.DamageType.THORNS), 1));
            blockTurn = true;
            amount++;

        }
        updateDescription();

    }



}
