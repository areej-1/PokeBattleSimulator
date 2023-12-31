// generate.js 
require('dotenv').config();
const { Configuration, OpenAIApi } = require('openai');

const apiKey = process.env.OPENAI_API_KEY;

if (!apiKey) {
    console.error("API Key not found!");
    process.exit(1);
}

const configuration = new Configuration({
    apiKey: apiKey,
});
const openai = new OpenAIApi(configuration);

(async function() {
  const input = process.argv[2] || '';

  if (input.trim().length === 0) {
    console.error("Please enter valid input");
    process.exit(1);
  }

  const messages = [
    {
      role: 'system',
      content: 'This is part of a text-based Pokemon game. You are to, given user input, which will either be a move name or an item name or a pokemon name, check if such a name exists in the given database. If it does exist, return the name in the proper format (capitalized, spellchecked). Otherwise, simply return: \'invalid.\' The database for Pokemon names is: Empoleon, Infernape, Mamoswine, Piloswine, Staraptor, Lucario, Garchomp, Luxray. The database for Pokemon moves is: Extreme Speed, Roost, U-Turn, Volt Switch, Avalanche, Waterfall, Swords Dance, Dragon Claw, Aerial Ace, Brave Bird, Iron Tail, Crunch, Thunder Fang, Dark Pulse, Flash Cannon, Aura Sphere, Ice Shard, Stone Edge, Ice Fang, Close Combat, Flare Blitz, Thunder Punch, Earthquake, Ice Beam, Thunderbolt, Surf, Acrobatics, Flame Wheel, Shadow Claw, Protect, Fly, ThunderWave, Spark, DragonBreath, TakeDown, IcyWind, DrainPunch, MeteorMash, Bite, AirSlash, Attract, Taunt. The database for item names is: Potion, Super Potion, Hyper Potion, Max Potion, Revive, Max Revive, Full Restore, Full Heal',
    },
    {
      role: 'user',
      content: 'User input:' + input
    },
  ];

  try {
    const completion = await openai.createChatCompletion({
      model: "gpt-4",
      messages: messages,
      temperature: 0.6,
      max_tokens: 1000,
    });
    
    const response = completion.data.choices[0].message['content'];
    console.log(response);
  } catch(error) {
    console.error(`Error with OpenAI API request: ${error.message}`);
    process.exit(1);
  }
})();
