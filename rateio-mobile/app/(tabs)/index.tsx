import { StatusBar } from 'expo-status-bar';
import { StyleSheet, Text, View, TouchableOpacity } from 'react-native';

export default function App() {
  return (
    <View style={styles.container}>
      <Text style={styles.title}>💸 Sistema de Rateio</Text>
      <Text style={styles.subtitle}>Divida as contas sem perder a amizade!</Text>

      <TouchableOpacity style={styles.button}>
        <Text style={styles.buttonText}>Entrar no App</Text>
      </TouchableOpacity>

      <StatusBar style="auto" />
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#F3F4F6', // Um cinza bem clarinho e moderno
    alignItems: 'center',
    justifyContent: 'center',
    padding: 20,
  },
  title: {
    fontSize: 28,
    fontWeight: 'bold',
    color: '#1F2937',
    marginBottom: 10,
  },
  subtitle: {
    fontSize: 16,
    color: '#6B7280',
    marginBottom: 40,
    textAlign: 'center',
  },
  button: {
    backgroundColor: '#10B981', // Verde sucesso
    paddingVertical: 15,
    paddingHorizontal: 40,
    borderRadius: 8,
    elevation: 3, // Sombrinha no Android
    shadowColor: '#000', // Sombrinha no iOS
    shadowOffset: { width: 0, height: 2 },
    shadowOpacity: 0.2,
    shadowRadius: 2,
  },
  buttonText: {
    color: '#FFFFFF',
    fontSize: 18,
    fontWeight: 'bold',
  }
});