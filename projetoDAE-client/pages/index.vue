<template>
  <Navbar activePage="home" />
  <div class="welcome-container">
    <h1>Welcome back, <b>{{ username }}</b>!</h1>
  </div>
  <div class="zone-container">
    <div class="cards-container">
      <NuxtLink to="/encomendas/show" class="card">
        <div class="card-image">
          <img src="/orders.png" alt="Orders">
        </div>
        <div class="card-content">
          <h2>Orders</h2>
          <p>View and manage your orders.</p>
        </div>
      </NuxtLink>
      <NuxtLink to="/sensors" class="card">
        <div class="card-image">
          <img src="/sensor.png" alt="Sensors">
        </div>
        <div class="card-content">
          <h2>Sensors</h2>
          <p>Monitor sensor data in real-time.</p>
        </div>
      </NuxtLink>
      <NuxtLink to="/products" class="card">
        <div class="card-image">
          <img src="/products.png" alt="Products">
        </div>
        <div class="card-content">
          <h2>Products</h2>
          <p>Manage your product inventory.</p>
        </div>
      </NuxtLink>
      <NuxtLink to="/volumes/show" class="card">
        <div class="card-image">
          <img src="/volume.png" alt="Volume">
        </div>
        <div class="card-content">
          <h2>Volume</h2>
          <p>Analyze storage and shipping volumes.</p>
        </div>
      </NuxtLink>
    </div>
  </div>
  <footer class="footer">
    Project by Cristiano Marques, João Roxo, Maria Malato, and Vasco Fernandes.
  </footer>
</template>


<script>
import Navbar from "@/components/navbar.vue";
import { ref, onMounted } from "vue";

export default {
  name: "IndexPage",
  components: {
    Navbar,
  },
  setup() {
    const username = ref(null);
    const userType = ref(null);

    onMounted(() => {
      if (typeof window !== "undefined") {
        username.value = sessionStorage.getItem("username") || "Guest";
        if(username.value == "Guest"){
          window.location.href = '/login';
        }
        userType.value = sessionStorage.getItem("userType");
      }

    });

    return {
      username,
      userType,
    };
  },
};
</script>

<style scoped>
body {
  font-family: Arial, sans-serif;
  background: linear-gradient(135deg, #f0f4f8, #d9e2ef);
  color: #333;
}

.welcome-container {
  text-align: center;
  margin: 30px 0;
}

.welcome-container h1 {
  font-size: 24px;
  color: #007bff;
}



.cards-container {
  display: grid;
  grid-template-columns: repeat(2, 1fr); /* Maintains 2x2 layout */
  gap: 20px;
  margin:0 6% 0 6%;
}

.card {
  display: flex;
  flex-direction: column; /* Make cards taller */
  align-items: center;
  justify-content: space-between;
  background: #f9fafb;
  padding: 30px;
  border-radius: 12px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  transition: transform 0.2s, box-shadow 0.2s, background-color 0.2s;
  border: 1px solid #ddd;
  height: 250px; /* Increase card height */
}

.card:hover {
  transform: translateY(-5px);
  box-shadow: 0 8px 12px rgba(0, 0, 0, 0.2);
  background-color: #f4f5f7;
  cursor: pointer;
}

.card-image {
  width: 100px;
  height: 100px;
  margin-bottom: 20px;
  border-radius: 12px;
  overflow: hidden;
}

.card-image img {
  width: 100%;
  height: 100%;
  object-fit: contain;
}

.card-content h2 {
  margin: 0;
  font-size: 20px;
  color: #0056b3;
  text-align: center;
}

.card-content p {
  margin: 10px 0 0;
  font-size: 14px;
  color: #666;
  text-align: center;
}

.footer {
  text-align: center;
  margin: 40px 0;
  font-size: 14px;
  color: #555;
  position: fixed;
  left: 50%;
  bottom: 0;
  transform: translateX(-50%);
  width: 100%;
  padding: 10px 0;
}


@media (max-width: 768px) {
  .cards-container {
    grid-template-columns: 1fr; /* Stacks cards on small screens */
  }
}
</style>
