package powers;


import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.unique.RipAndTearAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import mod.RitualistMod;

public class DemonArmUpgPower extends AbstractPower {
    public AbstractCreature source;

    public static final String POWER_ID = RitualistMod.makeID("DemonArmUpgPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = RitualistMod.makePath("customImages/buffPower.png");
    public int damage = 7;

    public DemonArmUpgPower(final AbstractCreature owner, final int amount, final int dmg) {
        name = NAME;
        ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        updateDescription();
        type = PowerType.BUFF;
        isTurnBased = false;
        img = new Texture(IMG);
        damage = dmg;


    }
    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + damage + DESCRIPTIONS[1] + amount  + DESCRIPTIONS[2];
    }

   @Override
    public void onAfterUseCard(AbstractCard card, UseCardAction action){
        if(card.type == AbstractCard.CardType.ATTACK) { //deal damage to number of enemies based on power stack
            AbstractDungeon.actionManager.addToBottom(new RipAndTearAction(AbstractDungeon.getMonsters().getRandomMonster(null,
                    true, AbstractDungeon.cardRandomRng),
                    new DamageInfo(owner, damage, DamageInfo.DamageType.THORNS), amount));
        }
   }

}
