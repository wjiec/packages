export default class IMissYou {
  constructor(title, useFavicon = false, faviconPath = null) {
    this.originalTitle = document.title;
    this.replaceTitle = title;

    this.useFavicon = false;
    this.originalFaviconElement = document.querySelector('link[rel$="icon"]');
    this.originalFaviconPath = this.originalFaviconElement ? this.originalFaviconElement.getAttribute('href') : null;
    this.replaceFaviconPath = faviconPath;
    if (useFavicon && this.originalFaviconPath && this.replaceFaviconPath) {
      this.useFavicon = true;
    }

    if (this.useFavicon) {
      this.prepareIMissYouFavicon();
    }
  }

  prepareIMissYouFavicon() {
    const image = document.createElement('img');
    image.src = this.replaceFaviconPath;
  }

  theGreatestHappinessWasToMeetYou() {
    document.addEventListener('visibilitychange', () => {
      if (this.replaceTitle) {
        document.title = document.hidden ? this.replaceTitle : this.originalTitle;
      }

      if (this.useFavicon) {
        if (document.hidden) {
          this.originalFaviconElement.href = this.replaceFaviconPath;
        } else {
          this.originalFaviconElement.href = this.originalFaviconPath;
        }
      }
    });
  }
}
