import { Link } from "react-router-dom";
import Footer from "../components/Footer";
import Navbar from "../components/Navbar";

function Home() {
  const features = [
    {
      icon: "🌾",
      title: "Crop Advisory",
      description:
          "Get personalized recommendations for crops, fertilizers, irrigation, and farming practices.",
      color: "green",
    },
    {
      icon: "🌦️",
      title: "Weather Insights",
      description:
          "Stay informed about weather conditions and plan your farming activities accordingly.",
      color: "blue",
    },
    {
      icon: "🩺",
      title: "Disease Detection",
      description:
          "Identify crop diseases early and get useful recommendations to protect your crops.",
      color: "amber",
    },
    {
      icon: "📊",
      title: "Smart Insights",
      description:
          "Use data-driven insights to improve productivity and make smarter farming decisions.",
      color: "purple",
    },
  ];

  return (
      <main className="bg-white text-gray-900 overflow-hidden">
        <Navbar />

        {/* =========================================================
          HERO
      ========================================================= */}
        <section className="relative w-full aspect-video overflow-hidden">

          {/* Background image */}
          <img
              src="/images/Hero.jpg"
              alt="Farmer working in a green agricultural field"
              className="absolute inset-0 w-full h-full object-cover scale-105"
          />

          {/* Dark premium overlay */}
          <div className="absolute inset-0 bg-gradient-to-r from-green-950/95 via-green-950/75 to-green-900/20" />

          {/* Bottom gradient */}
          <div className="absolute inset-x-0 bottom-0 h-40 bg-gradient-to-t from-black/30 to-transparent" />

          {/* Decorative glow */}
          <div className="absolute -top-32 -left-32 w-96 h-96 bg-green-400/20 rounded-full blur-3xl" />
          <div className="absolute bottom-0 right-0 w-96 h-96 bg-emerald-300/10 rounded-full blur-3xl" />

          {/* Content */}
          <div className="relative z-10 max-w-7xl mx-auto w-full px-5 sm:px-8 lg:px-10 py-24">

            <div className="max-w-3xl text-white">

              {/* Badge */}
              <div
                  className="
                inline-flex items-center gap-2
                px-4 py-2
                rounded-full
                bg-white/10
                border border-white/20
                backdrop-blur-md
                shadow-lg
                mb-7
              "
              >
                <span className="flex h-2 w-2 rounded-full bg-green-400 animate-pulse" />

                <span className="text-sm font-medium tracking-wide text-green-50">
                Smart Agriculture • Better Farming
              </span>
              </div>

              {/* Heading */}
              <h1
                  className="
                text-5xl sm:text-6xl lg:text-7xl
                font-black
                tracking-tight
                leading-[1.05]
              "
              >
                Grow Smarter.
                <span className="block text-green-300 mt-2">
                Farm Better.
              </span>
              </h1>

              {/* Description */}
              <p
                  className="
                mt-7
                text-base sm:text-lg lg:text-xl
                text-green-50/90
                leading-relaxed
                max-w-2xl
              "
              >
                AgriSathi brings intelligent crop advisory, weather insights,
                disease detection and modern farming technology together in
                one simple platform.
              </p>

              {/* Buttons */}
              <div className="mt-9 flex flex-col sm:flex-row gap-4">

                <Link
                    to="/register"
                    className="
                  group
                  inline-flex items-center justify-center
                  px-7 py-4
                  rounded-xl
                  bg-green-500
                  hover:bg-green-400
                  text-white
                  font-bold
                  shadow-xl shadow-green-950/30
                  hover:-translate-y-1
                  transition-all duration-300
                "
                >
                  Get Started

                  <span className="ml-3 group-hover:translate-x-1 transition-transform">
                  →
                </span>
                </Link>

                <a
                    href="#features"
                    className="
                  group
                  inline-flex items-center justify-center
                  px-7 py-4
                  rounded-xl
                  bg-white/10
                  hover:bg-white/20
                  border border-white/30
                  backdrop-blur-md
                  text-white
                  font-semibold
                  hover:-translate-y-1
                  transition-all duration-300
                "
                >
                  Explore Features
                  <span className="ml-3 group-hover:translate-y-1 transition-transform">
                  ↓
                </span>
                </a>

              </div>

              {/* Trust indicators */}
              <div className="mt-12 flex flex-wrap gap-x-8 gap-y-4">

                <div className="flex items-center gap-2 text-sm text-white/80">
                  <span className="text-green-300">✓</span>
                  Farmer-focused
                </div>

                <div className="flex items-center gap-2 text-sm text-white/80">
                  <span className="text-green-300">✓</span>
                  Smart insights
                </div>

                <div className="flex items-center gap-2 text-sm text-white/80">
                  <span className="text-green-300">✓</span>
                  Easy to use
                </div>

              </div>

            </div>
          </div>

          {/* Floating data card */}
          <div
              className="
            hidden xl:block
            absolute right-10 bottom-16
            w-72
            rounded-2xl
            bg-white/10
            backdrop-blur-xl
            border border-white/20
            p-5
            text-white
            shadow-2xl
          "
          >
            <div className="flex items-center justify-between mb-5">
              <div>
                <p className="text-xs text-white/60">
                  FARM STATUS
                </p>

                <p className="text-lg font-bold">
                  Crop Health
                </p>
              </div>

              <div className="h-10 w-10 rounded-xl bg-green-400/20 flex items-center justify-center">
                🌱
              </div>
            </div>

            <div className="h-2 rounded-full bg-white/10 overflow-hidden">
              <div className="h-full w-[88%] rounded-full bg-green-400" />
            </div>

            <div className="mt-3 flex justify-between text-sm">
            <span className="text-white/60">
              Overall health
            </span>

              <span className="font-semibold text-green-300">
              Excellent
            </span>
            </div>

            <div className="grid grid-cols-2 gap-3 mt-5">

              <div className="rounded-xl bg-white/5 p-3">
                <p className="text-xs text-white/50">
                  Weather
                </p>

                <p className="font-semibold mt-1">
                  28°C ☀️
                </p>
              </div>

              <div className="rounded-xl bg-white/5 p-3">
                <p className="text-xs text-white/50">
                  Soil
                </p>

                <p className="font-semibold mt-1">
                  Optimal
                </p>
              </div>

            </div>
          </div>

        </section>


        {/* =========================================================
          STATS
      ========================================================= */}
        <section className="relative bg-white border-b border-gray-100">

          <div className="max-w-7xl mx-auto px-5 sm:px-8 lg:px-10">

            <div className="grid grid-cols-2 md:grid-cols-4 divide-x divide-gray-100">

              <div className="py-9 text-center">
                <p className="text-3xl lg:text-4xl font-black text-green-700">
                  24/7
                </p>

                <p className="mt-2 text-sm text-gray-500">
                  Farmer Support
                </p>
              </div>

              <div className="py-9 text-center">
                <p className="text-3xl lg:text-4xl font-black text-green-700">
                  Smart
                </p>

                <p className="mt-2 text-sm text-gray-500">
                  Crop Advisory
                </p>
              </div>

              <div className="py-9 text-center">
                <p className="text-3xl lg:text-4xl font-black text-green-700">
                  AI
                </p>

                <p className="mt-2 text-sm text-gray-500">
                  Powered Insights
                </p>
              </div>

              <div className="py-9 text-center">
                <p className="text-3xl lg:text-4xl font-black text-green-700">
                  🌱
                </p>

                <p className="mt-2 text-sm text-gray-500">
                  Sustainable Farming
                </p>
              </div>

            </div>

          </div>
        </section>


        {/* =========================================================
          FEATURES
      ========================================================= */}
        <section
            id="features"
            className="relative py-24 bg-gradient-to-b from-gray-50 to-white"
        >

          <div className="max-w-7xl mx-auto px-5 sm:px-8 lg:px-10">

            {/* Section heading */}
            <div className="max-w-2xl mx-auto text-center mb-16">

            <span
                className="
                inline-flex
                px-4 py-2
                rounded-full
                bg-green-100
                text-green-700
                text-xs
                font-bold
                tracking-widest
              "
            >
              OUR FEATURES
            </span>

              <h2
                  className="
                mt-5
                text-3xl sm:text-4xl lg:text-5xl
                font-black
                tracking-tight
                text-gray-900
              "
              >
                Everything Farmers Need
              </h2>

              <p className="mt-5 text-gray-500 leading-relaxed">
                Powerful digital tools designed to help farmers make
                better decisions, reduce risk and improve productivity.
              </p>

            </div>


            {/* Feature cards */}
            <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">

              {features.map((feature) => (

                  <div
                      key={feature.title}
                      className="
                  group
                  relative
                  bg-white
                  rounded-3xl
                  p-7
                  border border-gray-100
                  shadow-sm
                  hover:shadow-2xl
                  hover:-translate-y-2
                  transition-all duration-300
                  overflow-hidden
                "
                  >

                    {/* Decorative circle */}
                    <div
                        className="
                    absolute -right-10 -top-10
                    w-28 h-28
                    rounded-full
                    bg-green-50
                    group-hover:scale-150
                    transition-transform duration-500
                  "
                    />

                    {/* Icon */}
                    <div
                        className="
                    relative
                    w-16 h-16
                    rounded-2xl
                    bg-green-50
                    border border-green-100
                    flex items-center justify-center
                    text-3xl
                    group-hover:scale-110
                    transition-transform duration-300
                  "
                    >
                      {feature.icon}
                    </div>

                    {/* Content */}
                    <h3 className="relative mt-7 text-xl font-bold text-gray-900">
                      {feature.title}
                    </h3>

                    <p className="relative mt-3 text-gray-500 leading-relaxed text-sm">
                      {feature.description}
                    </p>

                    {/* Bottom link */}
                    <div className="relative mt-6">

                  <span
                      className="
                      inline-flex items-center
                      text-sm font-bold
                      text-green-600
                      group-hover:text-green-700
                    "
                  >
                    Learn more
                    <span className="ml-2 group-hover:translate-x-1 transition-transform">
                      →
                    </span>
                  </span>

                    </div>

                  </div>

              ))}

            </div>

          </div>
        </section>


        {/* =========================================================
          ABOUT
      ========================================================= */}
        <section
            id="about"
            className="py-24 bg-white"
        >

          <div className="max-w-7xl mx-auto px-5 sm:px-8 lg:px-10">

            <div className="grid grid-cols-1 lg:grid-cols-2 gap-16 items-center">

              {/* Image */}
              <div className="relative">

                <div className="absolute -inset-4 bg-green-100 rounded-[2rem] -rotate-2" />

                <img
                    src="https://images.unsplash.com/photo-1625246333195-78d9c38ad449?auto=format&fit=crop&w=1200&q=85"
                    alt="Farmer working in field"
                    className="
                  relative
                  w-full
                  h-[430px]
                  object-cover
                  rounded-[2rem]
                  shadow-2xl
                "
                />

                {/* Floating badge */}
                <div
                    className="
                  absolute
                  -bottom-7
                  right-6
                  bg-white
                  rounded-2xl
                  px-6 py-5
                  shadow-xl
                  border border-gray-100
                "
                >

                  <div className="flex items-center gap-3">

                    <div className="w-12 h-12 rounded-xl bg-green-100 flex items-center justify-center text-2xl">
                      🌱
                    </div>

                    <div>
                      <p className="text-xs text-gray-400">
                        POWERED BY
                      </p>

                      <p className="font-bold text-gray-800">
                        Smart Farming
                      </p>
                    </div>

                  </div>

                </div>

              </div>


              {/* Content */}
              <div>

              <span className="text-sm font-bold tracking-widest text-green-600">
                ABOUT AGRISATHI
              </span>

                <h2
                    className="
                  mt-4
                  text-3xl sm:text-4xl lg:text-5xl
                  font-black
                  tracking-tight
                  text-gray-900
                "
                >
                  Technology Meets
                  <span className="block text-green-600">
                  Agriculture
                </span>
                </h2>

                <p className="mt-6 text-gray-500 leading-relaxed text-lg">
                  AgriSathi is designed to bridge the gap between modern
                  technology and traditional farming. Our goal is to provide
                  farmers with accessible and useful digital tools.
                </p>


                {/* Benefits */}
                <div className="mt-8 space-y-5">

                  <div className="flex gap-4">

                    <div className="flex-shrink-0 w-10 h-10 rounded-full bg-green-100 text-green-700 flex items-center justify-center font-bold">
                      ✓
                    </div>

                    <div>
                      <h4 className="font-bold text-gray-900">
                        Easy to Use
                      </h4>

                      <p className="text-sm text-gray-500 mt-1">
                        Simple tools designed with farmers in mind.
                      </p>
                    </div>

                  </div>


                  <div className="flex gap-4">

                    <div className="flex-shrink-0 w-10 h-10 rounded-full bg-green-100 text-green-700 flex items-center justify-center font-bold">
                      ✓
                    </div>

                    <div>
                      <h4 className="font-bold text-gray-900">
                        Data Driven
                      </h4>

                      <p className="text-sm text-gray-500 mt-1">
                        Make decisions using useful agricultural data.
                      </p>
                    </div>

                  </div>


                  <div className="flex gap-4">

                    <div className="flex-shrink-0 w-10 h-10 rounded-full bg-green-100 text-green-700 flex items-center justify-center font-bold">
                      ✓
                    </div>

                    <div>
                      <h4 className="font-bold text-gray-900">
                        Farmer Focused
                      </h4>

                      <p className="text-sm text-gray-500 mt-1">
                        Built to solve real-world farming challenges.
                      </p>
                    </div>

                  </div>

                </div>

              </div>

            </div>

          </div>

        </section>


        {/* =========================================================
          CTA
      ========================================================= */}
        <section className="relative overflow-hidden py-24">

          {/* Background */}
          <div className="absolute inset-0 bg-gradient-to-br from-green-900 via-green-800 to-emerald-700" />

          {/* Decorative circles */}
          <div className="absolute -top-32 -right-32 w-96 h-96 rounded-full bg-green-400/20 blur-3xl" />
          <div className="absolute -bottom-32 -left-32 w-96 h-96 rounded-full bg-emerald-300/10 blur-3xl" />

          <div className="relative max-w-4xl mx-auto px-5 text-center text-white">

            <div className="inline-flex w-16 h-16 items-center justify-center rounded-2xl bg-white/10 border border-white/20 text-3xl backdrop-blur-md">
              🌾
            </div>

            <h2
                className="
              mt-7
              text-3xl sm:text-4xl lg:text-5xl
              font-black
            "
            >
              Ready to Farm Smarter?
            </h2>

            <p className="mt-5 text-green-100 text-lg max-w-2xl mx-auto leading-relaxed">
              Join AgriSathi and bring smart technology to your
              farming journey.
            </p>

            <Link
                to="/register"
                className="
              group
              inline-flex items-center
              mt-9
              px-8 py-4
              rounded-xl
              bg-white
              text-green-700
              font-bold
              shadow-2xl
              hover:bg-green-50
              hover:-translate-y-1
              transition-all duration-300
            "
            >
              Join AgriSathi

              <span className="ml-3 group-hover:translate-x-1 transition-transform">
              →
            </span>
            </Link>

          </div>
        </section>


        <Footer />

      </main>
  );
}

export default Home;