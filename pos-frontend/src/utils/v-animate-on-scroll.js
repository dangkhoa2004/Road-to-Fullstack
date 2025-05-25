// directives/v-animate-on-scroll.js
export default {
  mounted(el) {
    const observer = new IntersectionObserver((entries) => {
      entries.forEach((entry) => {
        if (entry.isIntersecting) {
          el.classList.add('fade-in');
          observer.unobserve(el);
        }
      });
    }, {
      threshold: 0.1
    });

    observer.observe(el);
  }
};