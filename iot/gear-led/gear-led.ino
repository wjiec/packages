//
// - identifier "Serial" is undefined
//  https://github.com/microsoft/vscode-arduino/issues/808#issuecomment-490417186
//

#include <Adafruit_NeoPixel.h>

#define LED_STRIP_COUNT       10
#define LED_STRIP_GOIP        7
#define LED_STRIP_BRIGHTNESS  255
#define RPM_SYNC_INTERVAL     80

Adafruit_NeoPixel ledStrip(LED_STRIP_COUNT, LED_STRIP_GOIP, NEO_BGR + NEO_KHZ800);

static void rainbow();
static void markReady();
static uint16_t syncEngineRpm();
static void syncLedStrip();

void setup() {
  Serial.begin(115200);
  while (!Serial) delay(10);

  ledStrip.begin();
  ledStrip.setBrightness(LED_STRIP_BRIGHTNESS);

  Serial.println("AT DROFF\r");
}

static uint16_t rpm = 0;
static boolean ready = false;
static unsigned long currMills = 0;
static unsigned long prevSyncMills = 0;

void loop() {
  currMills = millis();

  if (!ready && !(ready = Serial.available())) {
    rainbow(); return;
  }

  if (currMills - prevSyncMills > RPM_SYNC_INTERVAL) {
    prevSyncMills = currMills;
    Serial.println("AT M10C\r");
  }

  if (syncEngineRpm() == 0) rainbow();
  else syncLedStrip();
}

static void rainbow() {
  static uint16_t hue = 0;
  static unsigned long rainbowMills = 0;

  if (currMills - rainbowMills > 50) {
    ledStrip.rainbow(hue, 1, 255, LED_STRIP_BRIGHTNESS, true);
    ledStrip.show();

    rainbowMills = currMills;
    hue += ((uint32_t)1 << 16) / LED_STRIP_COUNT;
  }
}

static uint16_t syncEngineRpm() {
  if (Serial.available()) {
    uint32_t curr = 0;
    String resp = Serial.readStringUntil('\n');
    for (auto &c : resp) {
      if (!isDigit(c)) break;
      curr = curr * 10 + (c - '0');
    }

    if (curr != 0) rpm = (uint16_t) curr;
  }

  return rpm;
}

struct colorful { uint8_t r, g, b, cycle; };
struct rule { uint16_t rpm; struct colorful colors[LED_STRIP_COUNT]; };

static struct rule rules[] = {
  {
    .rpm = 651,
    .colors = {
      {.r = 0xff, .g = 0xff, .b = 0x00, .cycle = 3}, // led0
      {.r = 0xff, .g = 0xff, .b = 0x00, .cycle = 3}, // led1
      {.r = 0xff, .g = 0xff, .b = 0x00, .cycle = 3}, // led2
      {.r = 0xff, .g = 0xff, .b = 0x00, .cycle = 3}, // led3
      {.r = 0xff, .g = 0xff, .b = 0x00, .cycle = 3}, // led4
      {.r = 0xff, .g = 0xff, .b = 0x00, .cycle = 3}, // led5
      {.r = 0xff, .g = 0xff, .b = 0x00, .cycle = 3}, // led6
      {.r = 0xff, .g = 0xff, .b = 0x00, .cycle = 3}, // led7
      {.r = 0xff, .g = 0xff, .b = 0x00, .cycle = 3}, // led8
      {.r = 0xff, .g = 0xff, .b = 0x00, .cycle = 3}, // led9
    }
  },
  {
    .rpm = 800,
    .colors = {
      {.r = 0x00, .g = 0xff, .b = 0x00, .cycle = 3}, // led0
      {.r = 0x00, .g = 0x00, .b = 0x00, .cycle = 0}, // led1
      {.r = 0x00, .g = 0x00, .b = 0x00, .cycle = 0}, // led2
      {.r = 0x00, .g = 0x00, .b = 0x00, .cycle = 0}, // led3
      {.r = 0x00, .g = 0x00, .b = 0x00, .cycle = 0}, // led4
      {.r = 0x00, .g = 0x00, .b = 0x00, .cycle = 0}, // led5
      {.r = 0x00, .g = 0x00, .b = 0x00, .cycle = 0}, // led6
      {.r = 0x00, .g = 0x00, .b = 0x00, .cycle = 0}, // led7
      {.r = 0x00, .g = 0x00, .b = 0x00, .cycle = 0}, // led8
      {.r = 0x00, .g = 0x00, .b = 0x00, .cycle = 0}, // led9
    }
  },
  {
    .rpm = 1133,
    .colors = {
      {.r = 0x00, .g = 0xff, .b = 0x00, .cycle = 0}, // led0
      {.r = 0x00, .g = 0x00, .b = 0x00, .cycle = 0}, // led1
      {.r = 0x00, .g = 0x00, .b = 0x00, .cycle = 0}, // led2
      {.r = 0x00, .g = 0x00, .b = 0x00, .cycle = 0}, // led3
      {.r = 0x00, .g = 0x00, .b = 0x00, .cycle = 0}, // led4
      {.r = 0x00, .g = 0x00, .b = 0x00, .cycle = 0}, // led5
      {.r = 0x00, .g = 0x00, .b = 0x00, .cycle = 0}, // led6
      {.r = 0x00, .g = 0x00, .b = 0x00, .cycle = 0}, // led7
      {.r = 0x00, .g = 0x00, .b = 0x00, .cycle = 0}, // led8
      {.r = 0x00, .g = 0x00, .b = 0x00, .cycle = 0}, // led9
    }
  },
  {
    .rpm = 1466,
    .colors = {
      {.r = 0x00, .g = 0xff, .b = 0x00, .cycle = 0}, // led0
      {.r = 0x00, .g = 0xff, .b = 0x00, .cycle = 0}, // led1
      {.r = 0x00, .g = 0x00, .b = 0x00, .cycle = 0}, // led2
      {.r = 0x00, .g = 0x00, .b = 0x00, .cycle = 0}, // led3
      {.r = 0x00, .g = 0x00, .b = 0x00, .cycle = 0}, // led4
      {.r = 0x00, .g = 0x00, .b = 0x00, .cycle = 0}, // led5
      {.r = 0x00, .g = 0x00, .b = 0x00, .cycle = 0}, // led6
      {.r = 0x00, .g = 0x00, .b = 0x00, .cycle = 0}, // led7
      {.r = 0x00, .g = 0x00, .b = 0x00, .cycle = 0}, // led8
      {.r = 0x00, .g = 0x00, .b = 0x00, .cycle = 0}, // led9
    }
  },
  {
    .rpm = 1800,
    .colors = {
      {.r = 0x00, .g = 0xff, .b = 0x00, .cycle = 0}, // led0
      {.r = 0x00, .g = 0xff, .b = 0x00, .cycle = 0}, // led1
      {.r = 0x00, .g = 0xff, .b = 0x00, .cycle = 0}, // led2
      {.r = 0x00, .g = 0x00, .b = 0x00, .cycle = 0}, // led3
      {.r = 0x00, .g = 0x00, .b = 0x00, .cycle = 0}, // led4
      {.r = 0x00, .g = 0x00, .b = 0x00, .cycle = 0}, // led5
      {.r = 0x00, .g = 0x00, .b = 0x00, .cycle = 0}, // led6
      {.r = 0x00, .g = 0x00, .b = 0x00, .cycle = 0}, // led7
      {.r = 0x00, .g = 0x00, .b = 0x00, .cycle = 0}, // led8
      {.r = 0x00, .g = 0x00, .b = 0x00, .cycle = 0}, // led9
    }
  },
  {
    .rpm = 2050,
    .colors = {
      {.r = 0x00, .g = 0xff, .b = 0x00, .cycle = 0}, // led0
      {.r = 0x00, .g = 0xff, .b = 0x00, .cycle = 0}, // led1
      {.r = 0x00, .g = 0xff, .b = 0x00, .cycle = 0}, // led2
      {.r = 0xff, .g = 0xff, .b = 0x00, .cycle = 1}, // led3
      {.r = 0x00, .g = 0x00, .b = 0x00, .cycle = 0}, // led4
      {.r = 0x00, .g = 0x00, .b = 0x00, .cycle = 0}, // led5
      {.r = 0x00, .g = 0x00, .b = 0x00, .cycle = 0}, // led6
      {.r = 0x00, .g = 0x00, .b = 0x00, .cycle = 0}, // led7
      {.r = 0x00, .g = 0x00, .b = 0x00, .cycle = 0}, // led8
      {.r = 0x00, .g = 0x00, .b = 0x00, .cycle = 0}, // led9
    }
  },
  {
    .rpm = 2300,
    .colors = {
      {.r = 0x00, .g = 0xff, .b = 0x00, .cycle = 0}, // led0
      {.r = 0x00, .g = 0xff, .b = 0x00, .cycle = 0}, // led1
      {.r = 0x00, .g = 0xff, .b = 0x00, .cycle = 0}, // led2
      {.r = 0xff, .g = 0xff, .b = 0x00, .cycle = 2}, // led3
      {.r = 0xff, .g = 0xff, .b = 0x00, .cycle = 2}, // led4
      {.r = 0x00, .g = 0x00, .b = 0x00, .cycle = 0}, // led5
      {.r = 0x00, .g = 0x00, .b = 0x00, .cycle = 0}, // led6
      {.r = 0x00, .g = 0x00, .b = 0x00, .cycle = 0}, // led7
      {.r = 0x00, .g = 0x00, .b = 0x00, .cycle = 0}, // led8
      {.r = 0x00, .g = 0x00, .b = 0x00, .cycle = 0}, // led9
    }
  },
  {
    .rpm = 2550,
    .colors = {
      {.r = 0x00, .g = 0xff, .b = 0x00, .cycle = 0}, // led0
      {.r = 0x00, .g = 0xff, .b = 0x00, .cycle = 0}, // led1
      {.r = 0x00, .g = 0xff, .b = 0x00, .cycle = 0}, // led2
      {.r = 0xff, .g = 0xff, .b = 0x00, .cycle = 2}, // led3
      {.r = 0xff, .g = 0xff, .b = 0x00, .cycle = 2}, // led4
      {.r = 0xff, .g = 0xff, .b = 0x00, .cycle = 2}, // led5
      {.r = 0x00, .g = 0x00, .b = 0x00, .cycle = 0}, // led6
      {.r = 0x00, .g = 0x00, .b = 0x00, .cycle = 0}, // led7
      {.r = 0x00, .g = 0x00, .b = 0x00, .cycle = 0}, // led8
      {.r = 0x00, .g = 0x00, .b = 0x00, .cycle = 0}, // led9
    }
  },
  {
    .rpm = 2800,
    .colors = {
      {.r = 0x00, .g = 0xff, .b = 0x00, .cycle = 0}, // led0
      {.r = 0x00, .g = 0xff, .b = 0x00, .cycle = 0}, // led1
      {.r = 0x00, .g = 0xff, .b = 0x00, .cycle = 0}, // led2
      {.r = 0xff, .g = 0xff, .b = 0x00, .cycle = 3}, // led3
      {.r = 0xff, .g = 0xff, .b = 0x00, .cycle = 3}, // led4
      {.r = 0xff, .g = 0xff, .b = 0x00, .cycle = 3}, // led5
      {.r = 0xff, .g = 0xff, .b = 0x00, .cycle = 3}, // led6
      {.r = 0x00, .g = 0x00, .b = 0x00, .cycle = 0}, // led7
      {.r = 0x00, .g = 0x00, .b = 0x00, .cycle = 0}, // led8
      {.r = 0x00, .g = 0x00, .b = 0x00, .cycle = 0}, // led9
    }
  },
  {
    .rpm = 3050,
    .colors = {
      {.r = 0x00, .g = 0xff, .b = 0x00, .cycle = 0}, // led0
      {.r = 0x00, .g = 0xff, .b = 0x00, .cycle = 0}, // led1
      {.r = 0x00, .g = 0xff, .b = 0x00, .cycle = 0}, // led2
      {.r = 0xff, .g = 0xff, .b = 0x00, .cycle = 3}, // led3
      {.r = 0xff, .g = 0xff, .b = 0x00, .cycle = 3}, // led4
      {.r = 0xff, .g = 0xff, .b = 0x00, .cycle = 3}, // led5
      {.r = 0xff, .g = 0xff, .b = 0x00, .cycle = 3}, // led6
      {.r = 0xff, .g = 0x00, .b = 0x00, .cycle = 3}, // led7
      {.r = 0x00, .g = 0x00, .b = 0x00, .cycle = 0}, // led8
      {.r = 0x00, .g = 0x00, .b = 0x00, .cycle = 0}, // led9
    }
  },
  {
    .rpm = 3300,
    .colors = {
      {.r = 0x00, .g = 0xff, .b = 0x00, .cycle = 0}, // led0
      {.r = 0x00, .g = 0xff, .b = 0x00, .cycle = 0}, // led1
      {.r = 0x00, .g = 0xff, .b = 0x00, .cycle = 0}, // led2
      {.r = 0xff, .g = 0xff, .b = 0x00, .cycle = 4}, // led3
      {.r = 0xff, .g = 0xff, .b = 0x00, .cycle = 4}, // led4
      {.r = 0xff, .g = 0xff, .b = 0x00, .cycle = 4}, // led5
      {.r = 0xff, .g = 0xff, .b = 0x00, .cycle = 4}, // led6
      {.r = 0xff, .g = 0x00, .b = 0x00, .cycle = 4}, // led7
      {.r = 0xff, .g = 0x00, .b = 0x00, .cycle = 4}, // led8
      {.r = 0x00, .g = 0x00, .b = 0x00, .cycle = 0}, // led9
    }
  },
  {
    .rpm = 3550,
    .colors = {
      {.r = 0x00, .g = 0xff, .b = 0x00, .cycle = 0}, // led0
      {.r = 0x00, .g = 0xff, .b = 0x00, .cycle = 0}, // led1
      {.r = 0x00, .g = 0xff, .b = 0x00, .cycle = 0}, // led2
      {.r = 0xff, .g = 0xff, .b = 0x00, .cycle = 5}, // led3
      {.r = 0xff, .g = 0xff, .b = 0x00, .cycle = 5}, // led4
      {.r = 0xff, .g = 0xff, .b = 0x00, .cycle = 5}, // led5
      {.r = 0xff, .g = 0xff, .b = 0x00, .cycle = 5}, // led6
      {.r = 0xff, .g = 0x00, .b = 0x00, .cycle = 5}, // led7
      {.r = 0xff, .g = 0x00, .b = 0x00, .cycle = 5}, // led8
      {.r = 0xff, .g = 0x00, .b = 0x00, .cycle = 5} // led9
    }
  },
  {
    .rpm = 19999,
    .colors = {
      {.r = 0xff, .g = 0x00, .b = 0x00, .cycle = 10}, // led0
      {.r = 0xff, .g = 0x00, .b = 0x00, .cycle = 10}, // led1
      {.r = 0xff, .g = 0x00, .b = 0x00, .cycle = 10}, // led2
      {.r = 0xff, .g = 0x00, .b = 0x00, .cycle = 10}, // led3
      {.r = 0xff, .g = 0x00, .b = 0x00, .cycle = 10}, // led4
      {.r = 0xff, .g = 0x00, .b = 0x00, .cycle = 10}, // led5
      {.r = 0xff, .g = 0x00, .b = 0x00, .cycle = 10}, // led6
      {.r = 0xff, .g = 0x00, .b = 0x00, .cycle = 10}, // led7
      {.r = 0xff, .g = 0x00, .b = 0x00, .cycle = 10}, // led8
      {.r = 0xff, .g = 0x00, .b = 0x00, .cycle = 10}, // led9
    }
  }
};

static void syncLedStrip() {
  int found = 0;
  while (rules[found].rpm <= rpm) found++;

  for (int i = 0; i < LED_STRIP_COUNT; i++) {
    auto color = rules[found].colors[i];
    ledStrip.setPixelColor(i, ledStrip.Color(color.r, color.g, color.b));
  }
  ledStrip.show();
}
