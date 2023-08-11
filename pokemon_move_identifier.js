// generate.js
const { Configuration, OpenAIApi } = require('openai');

const configuration = new Configuration({
  apiKey: 'key-is-here', // replace with your OpenAI key
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
      content: 'This is part of a text-based Pokemon game. The user must set the moves of their Pokemon, which they do by typing in input. You are to check said input and see whether or not it matches an existing move class, since moves are implemented as subclasses of a base move superclass. The existing classes of moves to check are: Surf, Thunderbolt, Flamethrower, IceBeam, Earthquake, ThunderPunch, FlareBlitz, CloseCombat, IceFang, StoneEdge, IceShard, AuraSphere, FlashCannon, DarkPulse, ThunderFang, Crunch, IronTail, BraveBird, AerialAce, UTurn, DragonClaw, SwordsDance, Waterfall, Avalanche, VoltSwitch, Roost, ExtremeSpeed. If you found a match between the user input and an existing class, return the name of the class. Note that typos are accepted, just make sure they are somewhat close to the original word(s). Otherwise, just return \'invalid input.\' Do not add anything to your output other than either the class name, or the words Invalid input. No punctuation for class names, as they will be directly used in the code.',
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
