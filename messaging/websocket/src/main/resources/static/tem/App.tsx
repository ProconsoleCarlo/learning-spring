import React, { useEffect, useState } from 'react';
import { Client } from '@stomp/stompjs';
import styled from 'styled-components';

interface Props {
  url: string;
  topic: string;
}

const MessageList = styled.ul`
  list-style: none;
  padding: 0;
`;

const MessageItem = styled.li`
  background-color: #f1f1f1;
  border-radius: 4px;
  margin-bottom: 8px;
  padding: 8px;
`;

const StompExample: React.FC<Props> = ({ url, topic }) => {
  const [client] = useState(new Client({ brokerURL: url }));
  const [messages, setMessages] = useState<string[]>([]);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    client.onConnect = () => {
      client.subscribe(
          topic,
          message => setMessages(prevMessages => [...prevMessages, message.body]),
          error => setError(`Error subscribing to topic ${topic}: ${error}`));
    };
    client.onStompError = frame => setError(`Broker reported error: ${frame.headers['message']}`);
    client.activate();
    return () => client.deactivate();
  }, [client]);

  return (
      <>
        {error && <p>{error}</p>}
        <MessageList>
          {messages.map((message, index) => (
              <MessageItem key={index}>{message}</MessageItem>
          ))}
        </MessageList>
      </>

  );
};

export default StompExample;