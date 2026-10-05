import { Link } from "react-router-dom";
import Footer from "../components/Footer";
import Navbar from "../components/Navbar";

function Home() {
  return (
    <main>
        <Navbar/>

      {/* ================= HERO ================= */}
      <section className="relative min-h-[650px] flex items-center overflow-hidden">

        {/* Hero Image */}
        <img
          src="https://images.unsplash.com/photo-1500382017468-9049fed747ef?auto=format&fit=crop&w=2000&q=85"
          alt="Green agricultural field"
          className="absolute inset-0 w-full h-full object-cover"
        />

        {/* Overlay */}
        <div className="absolute inset-0 bg-gradient-to-r from-green-950/90 via-green-900/70 to-green-900/20" />

        {/* Hero Content */}
        <div className="relative max-w-7xl mx-auto w-full px-4 sm:px-6 lg:px-8 py-20">

          <div className="max-w-2xl text-white">

            <div className="inline-flex items-center gap-2 bg-white/10
                            backdrop-blur-sm border border-white/20
                            rounded-full px-4 py-2 mb-6">
              <span>🌾</span>
              <span className="text-sm font-medium">
                Smart Agriculture for a Better Tomorrow
              </span>
            </div>

            <h1 className="text-4xl sm:text-5xl lg:text-6xl
                           font-extrabold leading-tight">
              Grow Smarter.
              <span className="block text-green-300">
                Farm Better.
              </span>
            </h1>

            <p className="mt-6 text-lg sm:text-xl text-green-50
                          leading-relaxed max-w-xl">
              AgriSathi helps farmers make better decisions with
              intelligent crop advisory, weather insights,
              disease detection, and modern farming technology.
            </p>

            <div className="mt-8 flex flex-col sm:flex-row gap-4">

              <Link
                to="/register"
                className="inline-flex items-center justify-center
                           px-7 py-3.5 rounded-xl
                           bg-green-500 hover:bg-green-400
                           text-white font-bold
                           shadow-lg hover:shadow-xl
                           transition"
              >
                Get Started
                <span className="ml-2">→</span>
              </Link>

              <a
                href="#features"
                className="inline-flex items-center justify-center
                           px-7 py-3.5 rounded-xl
                           bg-white/10 hover:bg-white/20
                           border border-white/30
                           backdrop-blur-sm
                           text-white font-semibold
                           transition"
              >
                Explore Features
              </a>

            </div>

          </div>
        </div>
      </section>


      {/* ================= STATS ================= */}
      <section className="bg-white border-b border-gray-100">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <div className="grid grid-cols-2 md:grid-cols-4">

            <div className="py-8 text-center border-r border-gray-100">
              <h3 className="text-3xl font-bold text-green-700">
                24/7
              </h3>
              <p className="text-gray-500 mt-1">
                Farmer Support
              </p>
            </div>

            <div className="py-8 text-center md:border-r border-gray-100">
              <h3 className="text-3xl font-bold text-green-700">
                Smart
              </h3>
              <p className="text-gray-500 mt-1">
                Crop Advisory
              </p>
            </div>

            <div className="py-8 text-center border-r border-gray-100">
              <h3 className="text-3xl font-bold text-green-700">
                AI
              </h3>
              <p className="text-gray-500 mt-1">
                Powered Insights
              </p>
            </div>

            <div className="py-8 text-center">
              <h3 className="text-3xl font-bold text-green-700">
                🌱
              </h3>
              <p className="text-gray-500 mt-1">
                Sustainable Farming
              </p>
            </div>

          </div>
        </div>
      </section>


      {/* ================= FEATURES ================= */}
      <section
        id="features"
        className="py-20 bg-gray-50"
      >
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">

          <div className="text-center max-w-2xl mx-auto mb-12">

            <span className="text-green-600 font-semibold">
              OUR FEATURES
            </span>

            <h2 className="text-3xl sm:text-4xl font-bold
                           text-gray-800 mt-2">
              Everything Farmers Need
            </h2>

            <p className="text-gray-500 mt-4">
              Powerful tools designed to help farmers improve
              productivity and make informed agricultural decisions.
            </p>

          </div>


          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">

            {/* Card 1 */}
            <div className="bg-white rounded-2xl p-6 shadow-sm
                            border border-gray-100
                            hover:shadow-xl hover:-translate-y-1
                            transition duration-300">

              <div className="w-14 h-14 rounded-xl bg-green-100
                              flex items-center justify-center text-3xl">
                🌾
              </div>

              <h3 className="text-xl font-bold text-gray-800 mt-5">
                Crop Advisory
              </h3>

              <p className="text-gray-500 mt-3 leading-relaxed">
                Get personalized recommendations for crops,
                fertilizers, irrigation, and farming practices.
              </p>

            </div>


            {/* Card 2 */}
            <div className="bg-white rounded-2xl p-6 shadow-sm
                            border border-gray-100
                            hover:shadow-xl hover:-translate-y-1
                            transition duration-300">

              <div className="w-14 h-14 rounded-xl bg-blue-100
                              flex items-center justify-center text-3xl">
                🌦️
              </div>

              <h3 className="text-xl font-bold text-gray-800 mt-5">
                Weather Insights
              </h3>

              <p className="text-gray-500 mt-3 leading-relaxed">
                Stay informed about weather conditions and plan
                your farming activities accordingly.
              </p>

            </div>


            {/* Card 3 */}
            <div className="bg-white rounded-2xl p-6 shadow-sm
                            border border-gray-100
                            hover:shadow-xl hover:-translate-y-1
                            transition duration-300">

              <div className="w-14 h-14 rounded-xl bg-yellow-100
                              flex items-center justify-center text-3xl">
                🩺
              </div>

              <h3 className="text-xl font-bold text-gray-800 mt-5">
                Disease Detection
              </h3>

              <p className="text-gray-500 mt-3 leading-relaxed">
                Identify crop diseases early and get useful
                recommendations to protect your crops.
              </p>

            </div>


            {/* Card 4 */}
            <div className="bg-white rounded-2xl p-6 shadow-sm
                            border border-gray-100
                            hover:shadow-xl hover:-translate-y-1
                            transition duration-300">

              <div className="w-14 h-14 rounded-xl bg-purple-100
                              flex items-center justify-center text-3xl">
                📊
              </div>

              <h3 className="text-xl font-bold text-gray-800 mt-5">
                Smart Insights
              </h3>

              <p className="text-gray-500 mt-3 leading-relaxed">
                Use data-driven insights to improve productivity
                and make smarter farming decisions.
              </p>

            </div>

          </div>
        </div>
      </section>


      {/* ================= ABOUT ================= */}
      <section id="about" className="py-20 bg-white">

        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">

          <div className="grid grid-cols-1 lg:grid-cols-2
                          gap-12 items-center">

            {/* Image */}
            <div className="relative">

              <img
                src="https://images.unsplash.com/photo-1625246333195-78d9c38ad449?auto=format&fit=crop&w=1200&q=85"
                alt="Farmer working in field"
                className="w-full h-[450px] object-cover rounded-3xl shadow-xl"
              />

              <div className="absolute -bottom-6 -right-4 sm:right-6
                              bg-green-600 text-white rounded-2xl
                              p-5 shadow-xl">
                <p className="text-3xl font-bold">
                  🌱
                </p>
                <p className="text-sm mt-1">
                  Smart Farming
                </p>
              </div>

            </div>


            {/* Content */}
            <div>

              <span className="text-green-600 font-semibold">
                ABOUT AGRISATHI
              </span>

              <h2 className="text-3xl sm:text-4xl font-bold
                             text-gray-800 mt-2">
                Technology Meets Agriculture
              </h2>

              <p className="text-gray-500 mt-5 leading-relaxed">
                AgriSathi is designed to bridge the gap between
                modern technology and traditional farming.
                Our goal is to provide farmers with accessible
                and useful digital tools.
              </p>

              <div className="mt-7 space-y-4">

                <div className="flex gap-4">
                  <div className="text-2xl">✓</div>
                  <div>
                    <h4 className="font-semibold text-gray-800">
                      Easy to Use
                    </h4>
                    <p className="text-gray-500 text-sm mt-1">
                      Simple tools designed with farmers in mind.
                    </p>
                  </div>
                </div>

                <div className="flex gap-4">
                  <div className="text-2xl">✓</div>
                  <div>
                    <h4 className="font-semibold text-gray-800">
                      Data Driven
                    </h4>
                    <p className="text-gray-500 text-sm mt-1">
                      Make decisions using useful agricultural data.
                    </p>
                  </div>
                </div>

                <div className="flex gap-4">
                  <div className="text-2xl">✓</div>
                  <div>
                    <h4 className="font-semibold text-gray-800">
                      Farmer Focused
                    </h4>
                    <p className="text-gray-500 text-sm mt-1">
                      Built to solve real-world farming challenges.
                    </p>
                  </div>
                </div>

              </div>

            </div>

          </div>
        </div>
      </section>


      {/* ================= CTA ================= */}
      <section className="py-20 bg-green-700">

        <div className="max-w-4xl mx-auto px-4 text-center text-white">

          <div className="text-5xl mb-5">
            🌾
          </div>

          <h2 className="text-3xl sm:text-4xl font-bold">
            Ready to Farm Smarter?
          </h2>

          <p className="mt-4 text-green-100 text-lg">
            Join AgriSathi and bring smart technology to
            your farming journey.
          </p>

          <Link
            to="/register"
            className="inline-block mt-8 px-8 py-3.5
                       bg-white text-green-700
                       rounded-xl font-bold
                       hover:bg-green-50
                       shadow-lg transition"
          >
            Join AgriSathi →
          </Link>

        </div>

      </section>

      <Footer/>

    </main>
  );
}

export default Home;