<template>
  <Navbar activePage="home" />
  <div class="welcome-container">
    <h1>Welcome back, <b>{{ username }}</b>!</h1>
  </div>
  <div class="cards-container">
    <div class="card">
      <div class="card-image">
        <img src="/orders.png" alt="Orders">
      </div>
      <div class="card-content">
        <h2>Orders</h2>
        <p>Details</p>
      </div>
    </div>
    <div class="card">
      <div class="card-image">
        <img src="/sensor.png" alt="Sensors">
      </div>
      <div class="card-content">
        <h2>Sensors</h2>
        <p>Details</p>
      </div>
    </div>
    <div class="card">
      <div class="card-image">
        <img src="/products.png" alt="Products">
      </div>
      <div class="card-content">
        <h2>Products</h2>
        <p>Details</p>
      </div>
    </div>
    <div class="card">
      <div class="card-image">
        <img src="/volume.png" alt="Volume">
      </div>
      <div class="card-content">
        <h2>Volume</h2>
        <p>Details</p>
      </div>
    </div>
  </div>
</template>

<script>
import Navbar from "@/components/navbar.vue";
import { ref, onMounted } from "vue";

export default {
  name: "IndexPage",
  setup() {
    const username = ref(null);

    // Certifique-se de que este código seja executado no lado do cliente
    onMounted(() => {
      if (typeof window !== "undefined") {
        username.value = sessionStorage.getItem("username") || "Guest";
      }
    });

    return {
      username,
    };
  },
};
</script>

<style scoped>

.welcome-container {
  text-align: center;
  margin: 20px 0;
}

.cards-container {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 20px;
  padding: 20px;
}

@media (max-width: 768px) {
  .cards-container {
    grid-template-columns: 1fr;
  }
}

.card {
  display: flex;
  align-items: center;
  background: #f8f9fa;
  padding: 20px;
  border: 1px solid #ddd;
  border-radius: 8px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  transition: transform 0.2s, box-shadow 0.2s;
}

.card:hover {
  transform: translateY(-5px);
  box-shadow: 0 6px 10px rgba(0, 0, 0, 0.2);
  cursor: pointer;
}


.card-image {
  flex-shrink: 0;
  width: 100px;
  height: 100px;
  overflow: hidden;
  border-radius: 8px;
  margin-right: 20px;
}

.card-image img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.card-content {
  flex-grow: 1;
}

.card-content h2 {
  margin: 0 0 10px;
  font-size: 18px;
  color: #333;
}

.card-content p {
  margin: 0;
  font-size: 14px;
  color: #666;
}
</style>
